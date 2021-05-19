package pl.piasta.hotel.domain.users;

import org.springframework.web.multipart.MultipartFile;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UpdateAccountStatusCommand;
import pl.piasta.hotel.domainmodel.users.UpdateUserPasswordCommand;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageCommand;

public interface UsersService {

    UsersPage getAllUsers(PageCommand command);
    void updateAccountStatus(Integer id, UpdateAccountStatusCommand command);
    void updateUserPassword(Integer id, UpdateUserPasswordCommand command);
    void updateUserAvatar(Integer id, MultipartFile file);
    void removeUserAvatar(Integer id);
    AvatarImage getUserAvatar(Integer id);
}
