package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.infrastructure.model.RolesEntity;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersEntityMapper {

    public Optional<UserInfo> mapToUserInfo(Optional<UsersEntity> usersEntity) {
        return usersEntity.map(e ->
                new UserInfo(
                        e.getId(),
                        e.getUsername(),
                        e.getEmail(),
                        e.getPassword(),
                        mapToRoleList(e.getRoles())));
    }

    private Set<Role> mapToRoleList(Set<RolesEntity> rolesEntityList) {
        return rolesEntityList.stream()
                .map(RolesEntity::getName)
                .collect(Collectors.toSet());
    }

}
