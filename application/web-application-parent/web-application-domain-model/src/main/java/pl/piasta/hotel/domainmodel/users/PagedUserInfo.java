package pl.piasta.hotel.domainmodel.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;
import pl.piasta.hotel.domainmodel.security.utils.Role;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public final class PagedUserInfo {

    private final Integer id;
    private final String username;
    private final String email;
    private final Set<Role> roles;
    private final AccountStatus status;
}
