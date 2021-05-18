package pl.piasta.hotel.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UpdateAccountStatusCommand;
import pl.piasta.hotel.domainmodel.users.UpdateUserPasswordCommand;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;
import pl.piasta.hotel.domainmodel.utils.FileUploadException;
import pl.piasta.hotel.domainmodel.utils.PageCommand;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.ResourceNotFoundException;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UsersPage getAllUsers(PageCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        return repository.getAllUsers(pageProperties, sortProperties);
    }

    @Override
    @Transactional
    public void updateAccountStatus(Integer id, UpdateAccountStatusCommand command) {
        if (!repository.updateAccountStatus(id, command.getStatus())) {
            throw new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void updateUserPassword(Integer id, UpdateUserPasswordCommand command) {
        String pasword = encoder.encode(command.getPassword());
        if (!repository.updateUserPassword(id, pasword)) {
            throw new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void updateUserAvatar(Integer id, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            AvatarImage avatarImage = new AvatarImage(fileName, file.getContentType(), file.getBytes());
            repository.updateUserAvatar(id, avatarImage);
        } catch (IOException ex) {
            throw new FileUploadException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AvatarImage getUserAvatar(Integer id) {
        return repository.getUserAvatar(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.USER_AVATAR_NOT_FOUND));
    }
}
