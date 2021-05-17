package pl.piasta.hotel.domainmodel.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateUserPasswordCommand {

    private String password;
}
