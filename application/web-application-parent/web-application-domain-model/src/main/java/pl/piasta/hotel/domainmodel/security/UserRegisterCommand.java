package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterCommand {

    private String username;
    private String email;
    private String password;
}
