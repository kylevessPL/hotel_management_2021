package pl.piasta.hotel.api.security.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.api.security.RefreshTokenRequest;
import pl.piasta.hotel.api.security.UserLoginRequest;
import pl.piasta.hotel.api.security.UserRegisterRequest;
import pl.piasta.hotel.domainmodel.security.RefreshTokenCommand;
import pl.piasta.hotel.domainmodel.security.RefreshTokenInfo;
import pl.piasta.hotel.domainmodel.security.TokenInfo;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.dto.security.RefreshTokenInfoResponse;
import pl.piasta.hotel.dto.security.TokenInfoResponse;

@Mapper
public interface AuthMapper {

    TokenInfoResponse mapToResponse(TokenInfo tokenInfo);
    RefreshTokenInfoResponse mapToResponse(RefreshTokenInfo tokenInfo);
    UserLoginCommand mapToCommand(UserLoginRequest request);
    UserRegisterCommand mapToCommand(UserRegisterRequest request);
    RefreshTokenCommand mapToCommand(RefreshTokenRequest request);
}
