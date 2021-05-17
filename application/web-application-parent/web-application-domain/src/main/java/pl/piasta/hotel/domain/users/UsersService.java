package pl.piasta.hotel.domain.users;

import pl.piasta.hotel.domainmodel.users.UpdateAccountStatusCommand;
import pl.piasta.hotel.domainmodel.users.UpdateUserPasswordCommand;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageCommand;

public interface UsersService {

    UsersPage getAllUsers(PageCommand command);
    void updateAccountStatus(Integer id, UpdateAccountStatusCommand command);
    void updateUserPassword(Integer id, UpdateUserPasswordCommand command);
}
