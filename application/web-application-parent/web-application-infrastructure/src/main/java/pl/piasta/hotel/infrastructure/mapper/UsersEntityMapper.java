package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.domainmodel.users.PagedUserInfo;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageMeta;
import pl.piasta.hotel.infrastructure.model.RolesEntity;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersEntityMapper {

    public UsersPage mapToUsersPage(
            List<UsersEntity> usersEntityList,
            boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext,
            Integer currentPage, Integer totalPage, Long totalCount) {
        List<PagedUserInfo> userInfoList = usersEntityList
                .stream()
                .map(this::createPagedUserInfo)
                .collect(Collectors.toList());
        return new UsersPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                userInfoList);
    }

    public Optional<UserInfo> mapToUserInfo(Optional<UsersEntity> usersEntity) {
        return usersEntity.map(e ->
                new UserInfo(
                        e.getId(),
                        e.getUsername(),
                        e.getEmail(),
                        e.getPassword(),
                        mapToRoleList(e.getRoles()),
                        e.getStatus()));
    }

    private Set<Role> mapToRoleList(Set<RolesEntity> rolesEntityList) {
        return rolesEntityList.stream()
                .map(RolesEntity::getName)
                .collect(Collectors.toSet());
    }

    private PagedUserInfo createPagedUserInfo(UsersEntity usersEntity) {
        return new PagedUserInfo(
                usersEntity.getId(),
                usersEntity.getUsername(),
                usersEntity.getEmail(),
                mapToRoleList(usersEntity.getRoles()),
                usersEntity.getStatus());
    }
}
