package pl.piasta.hotel.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piasta.hotel.domain.security.utils.JwtUtils;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserLoginInfo;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserLoginInfo loginUser(UserLoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return new UserLoginInfo(token,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                roles);
    }

    @Override
    public void registerUser(UserRegisterCommand command) {
        if (usernameExists(command.getUsername())) {
            throw new ApplicationException(ErrorCode.USERNAME_OCCUPIED);
        }
        if (emailExists(command.getEmail())) {
            throw new ApplicationException(ErrorCode.EMAIL_OCCUPIED);
        }
        repository.saveUser(
                command.getUsername(), command.getEmail(), encoder.encode(command.getPassword()),
                new HashSet<>(Collections.singleton(Role.ROLE_USER)));
    }

    private boolean usernameExists(String username) {
        return repository.userExistsByUsername(username);
    }

    private boolean emailExists(String email) {
        return repository.userExistsByEmail(email);
    }
}
