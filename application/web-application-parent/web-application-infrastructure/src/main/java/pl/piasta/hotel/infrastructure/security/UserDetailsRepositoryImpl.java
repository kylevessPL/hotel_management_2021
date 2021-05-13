package pl.piasta.hotel.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.security.UserDetailsRepository;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.infrastructure.dao.UsersEntityDao;
import pl.piasta.hotel.infrastructure.mapper.UsersEntityMapper;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDetailsRepositoryImpl implements UserDetailsRepository {

    private final UsersEntityMapper mapper;
    private final UsersEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findUserByUsername(String username) {
        Optional<UsersEntity> usersEntity = dao.findByUsername(username);
        return mapper.mapToUserInfo(usersEntity);
    }
}
