package pl.piasta.hotel.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter(onMethod = @__(@Override))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDetailsImpl implements UserDetails {

    @Getter
    @EqualsAndHashCode.Include
    private final Integer id;
    private final String username;
    @Getter
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    @Getter
    @JsonIgnore
    private final AccountStatus status;

    public static UserDetailsImpl build(UserInfo userInfo) {
        List<GrantedAuthority> authorities = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                userInfo.getId(),
                userInfo.getUsername(),
                userInfo.getEmail(),
                userInfo.getPassword(),
                authorities,
                userInfo.getStatus());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(AccountStatus.ACTIVE);
    }
}
