package pl.piasta.hotel.domain.security;

import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserLoginInfo;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;

public interface AuthService {

    UserLoginInfo loginUser(UserLoginCommand command);
    void registerUser(UserRegisterCommand command);
}
