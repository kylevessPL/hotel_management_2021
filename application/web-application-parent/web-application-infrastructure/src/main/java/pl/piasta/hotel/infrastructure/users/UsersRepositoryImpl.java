package pl.piasta.hotel.infrastructure.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.users.UsersRepository;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;
import pl.piasta.hotel.infrastructure.dao.UsersEntityDao;
import pl.piasta.hotel.infrastructure.mapper.UsersEntityMapper;
import pl.piasta.hotel.infrastructure.model.UsersEntity;
import pl.piasta.hotel.infrastructure.utils.SortUtils;

import java.util.Collections;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {

    private final UsersEntityMapper mapper;
    private final UsersEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public UsersPage getAllUsers(PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = SortUtils.createSortProperty(sortProperties);
        Page<UsersEntity> result = getAllPaged(page, size, sort);
        return mapper.mapToUsersPage(
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
        return dao.findById(id)
                .map(user -> {
                    updateEntity(user, status);
                    dao.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public boolean updateUserPassword(Integer id, String password) {
        return dao.findById(id)
                .map(user -> {
                    updateEntity(user, password);
                    dao.save(user);
                    return true;
                }).orElse(false);
    }

    private Page<UsersEntity> getAllPaged(Integer page, Integer size, Sort sort) {
        Page<UsersEntity> result = dao.findAll(PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = dao.findAll(PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    void updateEntity(UsersEntity user, AccountStatus status) {
        user.setStatus(status);
    }

    void updateEntity(UsersEntity user, String password) {
        user.setPassword(password);
    }
}
