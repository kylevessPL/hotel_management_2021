package pl.piasta.hotel.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.piasta.hotel.domain.bookings.BookingUtils;
import pl.piasta.hotel.domain.bookings.BookingsRepository;
import pl.piasta.hotel.domain.rooms.RoomsRepository;
import pl.piasta.hotel.domain.usersbookings.UsersBookingsRepository;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingInfo;
import pl.piasta.hotel.domainmodel.bookings.BookingsPage;
import pl.piasta.hotel.domainmodel.bookings.UserBookingsPage;
import pl.piasta.hotel.domainmodel.payments.PaymentStatus;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.domainmodel.users.UpdateAccountStatusCommand;
import pl.piasta.hotel.domainmodel.users.UpdateUserPasswordCommand;
import pl.piasta.hotel.domainmodel.users.UsersPage;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;
import pl.piasta.hotel.domainmodel.utils.FileUploadException;
import pl.piasta.hotel.domainmodel.utils.PageCommand;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.ResourceNotFoundException;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BookingsRepository bookingsRepository;
    private final RoomsRepository roomsRepository;
    private final UsersBookingsRepository usersBookingsRepository;

    private final BookingUtils bookingUtils;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UsersPage getAllUsers(PageCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        return usersRepository.getAllUsers(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public UserBookingsPage getAllUserBookings(Integer id, PageCommand command) {
        BookingsPage bookingsPage = getBookingsPage(id, command);
        List<BookingFinalDetails> bookingFinalDetails = bookingsPage.getContent();
        List<BookingInfo> bookingInfoList = bookingFinalDetails.stream().map(e -> {
            RoomFinalDetails roomFinalDetails = getRoomFinalDetails(e.getRoomId());
            RoomInfo roomInfo = bookingUtils.createRoomInfo(roomFinalDetails);
            PaymentStatus paymentStatus = bookingUtils.createPaymentStatus(id, e.getBookingStatus());
            return new BookingInfo(e.getBookingDate(), roomInfo, e.getFinalPrice(), paymentStatus);
        }).collect(Collectors.toList());
        return new UserBookingsPage(bookingsPage.getMeta(), bookingInfoList);
    }

    private RoomFinalDetails getRoomFinalDetails(Integer id) {
        return roomsRepository.getRoomFinalDetails(id);
    }

    private BookingsPage getBookingsPage(Integer id, PageCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        List<Integer> idList = usersBookingsRepository.getAllUserBookingsIdList(id);
        return bookingsRepository.getAllBookingsInList(idList, pageProperties, sortProperties);
    }

    @Override
    @Transactional
    public void updateAccountStatus(Integer id, UpdateAccountStatusCommand command) {
        if (!usersRepository.updateAccountStatus(id, command.getStatus())) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateUserPassword(Integer id, UpdateUserPasswordCommand command) {
        String pasword = encoder.encode(command.getPassword());
        if (!usersRepository.updateUserPassword(id, pasword)) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateUserAvatar(Integer id, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            AvatarImage avatarImage = new AvatarImage(fileName, file.getContentType(), file.getBytes());
            usersRepository.updateUserAvatar(id, avatarImage);
        } catch (IOException ex) {
            throw new FileUploadException();
        }
    }

    @Override
    @Transactional
    public void removeUserAvatar(Integer id) {
        usersRepository.removeUserAvatar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AvatarImage getUserAvatar(Integer id) {
        return usersRepository.getUserAvatar(id).orElseThrow(() ->
                new ApplicationException(ErrorCode.USER_AVATAR_NOT_FOUND));
    }
}
