package pl.piasta.hotel.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.piasta.hotel.domain.security.UserDetailsImpl;

@Component
public class SecurityControl {

    public boolean hasPermission(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal()).getId().equals(id);
    }
}
