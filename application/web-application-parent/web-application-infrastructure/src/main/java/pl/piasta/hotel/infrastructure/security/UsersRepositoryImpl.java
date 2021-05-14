package pl.piasta.hotel.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.security.UsersRepository;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.infrastructure.dao.RolesEntityDao;
import pl.piasta.hotel.infrastructure.dao.UsersEntityDao;
import pl.piasta.hotel.infrastructure.mapper.UsersEntityMapper;
import pl.piasta.hotel.infrastructure.model.RolesEntity;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {

    private final UsersEntityMapper mapper;
    private final UsersEntityDao usersEntityDao;
    private final RolesEntityDao rolesEntityDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findUserByUsername(String username) {
        Optional<UsersEntity> usersEntity = usersEntityDao.findByUsername(username);
        return mapper.mapToUserInfo(usersEntity);
    }

    @Override
    @Transactional
    public void saveUser(String username, String email, String password, Set<Role> roles) {
        Set<RolesEntity> rolesEntitySet = roles.stream()
                .map(rolesEntityDao::getByName)
                .collect(Collectors.toSet());
        UsersEntity usersEntity = new UsersEntity();
        updateEntity(usersEntity, username, email, password, rolesEntitySet);
        usersEntityDao.save(usersEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsByUsername(String username) {
        return usersEntityDao.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsByEmail(String email) {
        return usersEntityDao.existsByEmail(email);
    }

    void updateEntity(UsersEntity user, String username, String email, String password, Set<RolesEntity> roles) {
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
    }
}
