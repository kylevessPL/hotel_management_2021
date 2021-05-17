package pl.piasta.hotel.domainmodel.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;

@NoArgsConstructor
@Getter
@Setter
public class UpdateAccountStatusCommand {

    private AccountStatus status;
}
