package pl.piasta.hotel.domain.users;

import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

public interface UsersRepository {

    UsersPage getAllUsers(PageProperties pageProperties, SortProperties sortProperties);
    boolean updateAccountStatus(Integer id, AccountStatus status);
    boolean updateUserPassword(Integer id, String password);
}
