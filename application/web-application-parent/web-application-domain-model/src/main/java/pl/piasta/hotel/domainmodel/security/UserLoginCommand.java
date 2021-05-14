package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginCommand {

    private String username;
    private String password;
}
