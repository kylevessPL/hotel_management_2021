package pl.piasta.hotel.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.security.AuthRepository;
import pl.piasta.hotel.domainmodel.security.RefreshToken;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.infrastructure.dao.RefreshTokensEntityDao;
import pl.piasta.hotel.infrastructure.dao.RolesEntityDao;
import pl.piasta.hotel.infrastructure.dao.UsersEntityDao;
import pl.piasta.hotel.infrastructure.mapper.RefreshTokensEntityMapper;
import pl.piasta.hotel.infrastructure.mapper.UsersEntityMapper;
import pl.piasta.hotel.infrastructure.model.RefreshTokensEntity;
import pl.piasta.hotel.infrastructure.model.RolesEntity;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final UsersEntityMapper usersEntityMapper;
    private final RefreshTokensEntityMapper refreshTokensEntityMapper;
    private final UsersEntityDao usersEntityDao;
    private final RolesEntityDao rolesEntityDao;
    private final RefreshTokensEntityDao refreshTokensEntityDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findUserByUsername(String username) {
        Optional<UsersEntity> usersEntity = usersEntityDao.findByUsername(username);
        return usersEntityMapper.mapToUserInfo(usersEntity);
    }

    @Override
    @Transactional
    public void saveUser(String username, String email, String password, Set<Role> roles) {
        Set<RolesEntity> rolesEntitySet = roles.stream()
                .map(rolesEntityDao::getByName)
                .collect(Collectors.toSet());
        UsersEntity usersEntity = new UsersEntity();
        updateUsersEntity(usersEntity, username, email, password, rolesEntitySet);
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

    @Override
    @Transactional
    public void saveRefreshToken(RefreshToken refreshToken) {
        UsersEntity usersEntity = usersEntityDao.getByUsername(refreshToken.getSubject());
        RefreshTokensEntity refreshTokensEntity = new RefreshTokensEntity();
        updateRefreshTokensEntity(refreshTokensEntity, usersEntity, refreshToken);
        refreshTokensEntityDao.save(refreshTokensEntity);
    }

    @Override
    @Transactional
    public Optional<RefreshToken> deleteRefreshTokenByToken(String token) {
        List<RefreshTokensEntity> deleted = refreshTokensEntityDao.deleteByToken(token);
        if (deleted.size() != 0) {
            return refreshTokensEntityMapper.mapToTokenInfo(Optional.of(deleted.get(0)));
        }
        return Optional.empty();
    }

    void updateUsersEntity(UsersEntity user, String username, String email, String password, Set<RolesEntity> roles) {
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
    }

    void updateRefreshTokensEntity(
            RefreshTokensEntity refreshTokensEntity,
            UsersEntity usersEntity, RefreshToken refreshToken) {
        refreshTokensEntity.setToken(refreshToken.getToken());
        refreshTokensEntity.setExpiryDate(refreshToken.getExpiryDate());
        refreshTokensEntity.setUser(usersEntity);
    }
}
