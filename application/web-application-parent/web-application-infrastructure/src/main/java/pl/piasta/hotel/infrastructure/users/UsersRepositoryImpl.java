package pl.piasta.hotel.infrastructure.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.users.UsersRepository;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;
import pl.piasta.hotel.infrastructure.dao.FilesEntityDao;
import pl.piasta.hotel.infrastructure.dao.UsersEntityDao;
import pl.piasta.hotel.infrastructure.mapper.FilesEntityMapper;
import pl.piasta.hotel.infrastructure.mapper.UsersEntityMapper;
import pl.piasta.hotel.infrastructure.model.FilesEntity;
import pl.piasta.hotel.infrastructure.model.UsersEntity;
import pl.piasta.hotel.infrastructure.utils.SortUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {

    private final UsersEntityMapper usersEntityMapper;
    private final FilesEntityMapper filesEntityMapper;
    private final UsersEntityDao usersEntityDao;
    private final FilesEntityDao filesEntityDao;

    @Override
    @Transactional(readOnly = true)
    public UsersPage getAllUsers(PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = SortUtils.createSortProperty(sortProperties);
        Page<UsersEntity> result = getAllPaged(page, size, sort);
        return usersEntityMapper.mapToUsersPage(
                result.hasContent() ? result.getContent() : Collections.emptyList(),
                result.isFirst(),
                result.isLast(),
                result.hasPrevious(),
                result.hasNext(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.getTotalElements());
    }

    @Override
    @Transactional
    public boolean updateAccountStatus(Integer id, AccountStatus status) {
        return usersEntityDao.findById(id)
                .map(user -> {
                    updateEntity(user, status);
                    usersEntityDao.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public boolean updateUserPassword(Integer id, String password) {
        return usersEntityDao.findById(id)
                .map(user -> {
                    updateEntity(user, password);
                    usersEntityDao.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public void updateUserAvatar(Integer id, AvatarImage file) {
        FilesEntity filesEntity = new FilesEntity();
        updateEntity(filesEntity, file);
        UUID uuid = filesEntityDao.save(filesEntity).getId();
        UsersEntity usersEntity = usersEntityDao.getOne(id);
        updateEntity(usersEntity, uuid);
        usersEntityDao.save(usersEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvatarImage> getUserAvatar(Integer id) {
        UUID uuid = usersEntityDao.getOne(id).getImageId();
        if (uuid != null) {
            return filesEntityMapper.mapToAvatarImage(filesEntityDao.findById(uuid));
        }
        return Optional.empty();
    }

    private Page<UsersEntity> getAllPaged(Integer page, Integer size, Sort sort) {
        Page<UsersEntity> result = usersEntityDao.findAll(PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = usersEntityDao.findAll(PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    void updateEntity(UsersEntity user, AccountStatus status) {
        user.setStatus(status);
    }

    void updateEntity(UsersEntity user, String password) {
        user.setPassword(password);
    }

    void updateEntity(UsersEntity user, UUID imageId) {
        user.setImageId(imageId);
    }

    void updateEntity(FilesEntity file, AvatarImage image) {
        file.setName(image.getName());
        file.setType(image.getType());
        file.setData(image.getData());
    }
}
