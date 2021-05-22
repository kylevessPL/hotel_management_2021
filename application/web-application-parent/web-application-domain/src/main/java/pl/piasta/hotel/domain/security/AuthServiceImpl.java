package pl.piasta.hotel.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.security.utils.JwtUtils;
import pl.piasta.hotel.domainmodel.security.RefreshToken;
import pl.piasta.hotel.domainmodel.security.RefreshTokenCommand;
import pl.piasta.hotel.domainmodel.security.RefreshTokenInfo;
import pl.piasta.hotel.domainmodel.security.TokenInfo;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Value("${app.security.access-token.expiration}")
    private int jwtExpirationMs;
    @Value("${app.security.refresh-token.expiration}")
    private int jwtRefreshExpirationMs;

    @Override
    @Transactional
    public TokenInfo authenticateUser(UserLoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessTokenFromUsername(userDetails.getUsername());
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return new TokenInfo(
                accessToken, generateRefreshToken(userDetails.getUsername()), jwtExpirationMs / 1000,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                roles);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public RefreshTokenInfo refreshToken(RefreshTokenCommand command) {
        return repository.deleteRefreshTokenByToken(command.getToken())
                .map(this::checkRefreshTokenValidity)
                .map(RefreshToken::getSubject)
                .map(subject -> {
                    String accessToken = jwtUtils.generateAccessTokenFromUsername(subject);
                    String refreshToken = generateRefreshToken(subject);
                    return new RefreshTokenInfo(accessToken, refreshToken, jwtExpirationMs / 1000);
                })
                .orElseThrow(() -> new ApplicationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    private String generateRefreshToken(String subject) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusNanos((long) jwtRefreshExpirationMs * 1000000),
                subject);
        repository.saveRefreshToken(refreshToken);
        return refreshToken.getToken();
    }

    private RefreshToken checkRefreshTokenValidity(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            throw new ApplicationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return token;
    }

    private boolean usernameExists(String username) {
        return repository.userExistsByUsername(username);
    }

    private boolean emailExists(String email) {
        return repository.userExistsByEmail(email);
    }
}
