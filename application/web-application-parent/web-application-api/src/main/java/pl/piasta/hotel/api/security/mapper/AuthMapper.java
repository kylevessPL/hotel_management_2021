package pl.piasta.hotel.api.security.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.api.security.UserLoginRequest;
import pl.piasta.hotel.api.security.UserRegisterRequest;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserLoginInfo;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.dto.security.UserLoginInfoResponse;

@Mapper
public interface AuthMapper {

    UserLoginInfoResponse mapToResponse(UserLoginInfo userLoginInfo);
    UserLoginCommand mapToCommand(UserLoginRequest request);
    UserRegisterCommand mapToCommand(UserRegisterRequest request);
}
