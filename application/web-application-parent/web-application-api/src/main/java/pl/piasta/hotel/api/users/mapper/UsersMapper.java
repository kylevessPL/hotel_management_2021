package pl.piasta.hotel.api.users.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.api.users.UpdateAccountStatusRequest;
import pl.piasta.hotel.api.users.UpdateUserPasswordRequest;
import pl.piasta.hotel.api.users.UsersPageQuery;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UpdateAccountStatusCommand;
import pl.piasta.hotel.domainmodel.users.UpdateUserPasswordCommand;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.PageCommand;
import pl.piasta.hotel.dto.users.AvatarImageResponse;
import pl.piasta.hotel.dto.users.UsersPageResponse;

@Mapper
public interface UsersMapper {

    PageCommand mapToCommand(UsersPageQuery query);
    UpdateAccountStatusCommand mapToCommand(UpdateAccountStatusRequest request);
    UpdateUserPasswordCommand mapToCommand(UpdateUserPasswordRequest request);
    UsersPageResponse mapToResponse(UsersPage usersPage);
    AvatarImageResponse mapToResponse(AvatarImage avatarImage);
}
