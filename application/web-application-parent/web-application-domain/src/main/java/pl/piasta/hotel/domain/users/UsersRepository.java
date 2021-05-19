package pl.piasta.hotel.domain.users;

import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.util.Optional;

public interface UsersRepository {

    UsersPage getAllUsers(PageProperties pageProperties, SortProperties sortProperties);
    boolean updateAccountStatus(Integer id, AccountStatus status);
    boolean updateUserPassword(Integer id, String password);
    void updateUserAvatar(Integer id, AvatarImage file);
    void removeUserAvatar(Integer id);
    Optional<AvatarImage> getUserAvatar(Integer id);
}
