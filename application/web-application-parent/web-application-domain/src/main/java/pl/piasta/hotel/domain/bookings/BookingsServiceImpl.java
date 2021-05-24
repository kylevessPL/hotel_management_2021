package pl.piasta.hotel.domain.bookings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.additionalservices.AdditionalServicesRepository;
import pl.piasta.hotel.domain.bookingscustomers.BookingsCustomersRepository;
import pl.piasta.hotel.domain.bookingsservices.BookingsServicesRepository;
import pl.piasta.hotel.domain.customers.CustomersRepository;
import pl.piasta.hotel.domain.discounts.DiscountsRepository;
import pl.piasta.hotel.domain.paymentforms.PaymentFormsRepository;
import pl.piasta.hotel.domain.payments.PaymentsRepository;
import pl.piasta.hotel.domain.rooms.RoomsRepository;
import pl.piasta.hotel.domain.usersbookings.UsersBookingsRepository;
import pl.piasta.hotel.domainmodel.additionalservices.AdditionalService;
import pl.piasta.hotel.domainmodel.bookings.Booking;
import pl.piasta.hotel.domainmodel.bookings.BookingCancellationDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingCommand;
import pl.piasta.hotel.domainmodel.bookings.BookingDate;
import pl.piasta.hotel.domainmodel.bookings.BookingDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingInfo;
import pl.piasta.hotel.domainmodel.bookings.BookingStatus;
import pl.piasta.hotel.domainmodel.customers.CustomerDetails;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;
import pl.piasta.hotel.domainmodel.payments.PaymentStatus;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;
import pl.piasta.hotel.domainmodel.utils.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
public class BookingsServiceImpl implements BookingsService {

    private final BookingsRepository bookingsRepository;
    private final DiscountsRepository discountsRepository;
    private final BookingsCustomersRepository bookingsCustomersRepository;
    private final AdditionalServicesRepository additionalServicesRepository;
    private final BookingsServicesRepository bookingsServicesRepository;
    private final UsersBookingsRepository usersBookingsRepository;
    private final CustomersRepository customersRepository;
    private final RoomsRepository roomsRepository;
    private final PaymentFormsRepository paymentFormsRepository;
    private final PaymentsRepository paymentsRepository;

    private final BookingUtils bookingUtils;

    @Override
    @Transactional
    public Booking makeBooking(Integer userId, BookingCommand command) {
        Optional<DiscountDetails> discountDetails = getDiscountDetails(command.getDiscountCode());
        RoomDetails roomDetails = getRoomDetails(command.getRoomId(), command.getDateDetails());
        checkCustomersValidity(roomDetails, command.getCustomers());
        List<AdditionalService> additionalServicesList = getAdditionalServices(command.getAdditionalServices());
        List<PaymentForm> paymentFormList = getPaymentForms();
        BigDecimal finalPrice = calculateFinalPrice(roomDetails, additionalServicesList, discountDetails, command);
        BookingDetails bookingDetails = createBookingDetails(command.getDateDetails(), roomDetails, finalPrice);
        Integer bookingId = saveBooking(userId, bookingDetails);
        saveCustomers(bookingId, command.getCustomers());
        saveBookingServices(bookingId, additionalServicesList);
        return createBookingSummary(paymentFormList, finalPrice, bookingId);
    }

    @Override
    @Transactional
    public void cancelBooking(Integer id) {
        BookingCancellationDetails bookingCancellationDetails = getBookingCancellationDetails(id);
        checkBookingValidity(bookingCancellationDetails.getBookingStatus(), bookingCancellationDetails.getBookingDate());
        bookingsRepository.cancelBooking(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingInfo getBookingInfo(Integer id) {
        BookingFinalDetails bookingFinalDetails = getBookingFinalDetails(id);
        RoomFinalDetails roomFinalDetails = getRoomFinalDetails(bookingFinalDetails.getRoomId());
        PaymentStatus paymentStatus = bookingUtils.createPaymentStatus(id, bookingFinalDetails.getBookingStatus());
        return createBookingInfo(bookingFinalDetails, roomFinalDetails, paymentStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(Integer userId, Integer bookingId) {
        return usersBookingsRepository.getBookingUserId(bookingId)
                .map(id -> id.equals(userId))
                .orElse(true);
    }

    private BookingInfo createBookingInfo(BookingFinalDetails bookingFinalDetails, RoomFinalDetails roomFinalDetails, PaymentStatus paymentStatus) {
        BookingDate bookingDate = createBookingDate(bookingFinalDetails);
        RoomInfo roomInfo = bookingUtils.createRoomInfo(roomFinalDetails);
        return new BookingInfo(bookingDate, roomInfo, paymentStatus);
    }

    private BookingDate createBookingDate(BookingFinalDetails bookingDetails) {
        return new BookingDate(
                bookingDetails.getBookingDate().getBookDate(),
                bookingDetails.getBookingDate().getStartDate(),
                bookingDetails.getBookingDate().getEndDate());
    }

    private RoomFinalDetails getRoomFinalDetails(Integer id) {
        return roomsRepository.getRoomFinalDetails(id);
    }

    private BookingFinalDetails getBookingFinalDetails(Integer id) {
        return bookingsRepository.getBookingFinalDetails(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private BookingCancellationDetails getBookingCancellationDetails(Integer bookingId) {
        return bookingsRepository.getBookingCancellationDetails(bookingId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Booking createBookingSummary(List<PaymentForm> paymentFormList, BigDecimal finalPrice, Integer bookingId) {
        return new Booking(bookingId, finalPrice, paymentFormList);
    }

    private void saveCustomers(Integer bookingId, List<CustomerDetails> customerDetails) {
        customerDetails.forEach(customer -> {
            Integer id = customersRepository.saveCustomer(customer);
            bookingsCustomersRepository.saveBookingCustomer(bookingId, id);
        });
    }

    private Integer saveBooking(Integer userId, BookingDetails bookingDetails) {
        Integer bookingId = bookingsRepository.saveBooking(bookingDetails);
        usersBookingsRepository.saveUserBooking(userId, bookingId);
        return bookingId;
    }

    private void saveBookingServices(Integer bookingId, List<AdditionalService> additionalServicesList) {
        if(!additionalServicesList.isEmpty()) {
            List<Integer> additionalServices = additionalServicesList
                    .stream()
                    .map(AdditionalService::getId)
                    .collect(Collectors.toList());
            bookingsServicesRepository.saveBookingServices(bookingId, additionalServices);
        }
    }

    private BookingDetails createBookingDetails(DateDetails dateDetails, RoomDetails roomDetails, BigDecimal finalPrice) {
        return new BookingDetails(dateDetails, roomDetails, finalPrice);
    }

    private List<PaymentForm> getPaymentForms() {
        return paymentFormsRepository.getAllPaymentForms();
    }

    private List<AdditionalService> getAdditionalServices(Integer[] additionalServices) {
        if(additionalServices == null || additionalServices.length == 0) {
            return Collections.emptyList();
        }
        return additionalServicesRepository.getAdditionalServices(Arrays.asList(additionalServices))
                .orElseThrow(() -> new ApplicationException(ErrorCode.ADDITIONAL_SERVICE_NOT_FOUND));
    }

    private RoomDetails getRoomDetails(Integer roomId, DateDetails dateDetails) {
        if(!isRoomAvailable(roomId, dateDetails)) {
            throw new ApplicationException(ErrorCode.ROOM_NOT_AVAILABLE);
        }
        return roomsRepository.getRoomDetails(roomId).orElseThrow(() -> new ApplicationException(ErrorCode.ROOM_NOT_FOUND));
    }

    private boolean isRoomAvailable(Integer roomId, DateDetails dateDetails) {
        return !bookingsRepository.getBookingsRoomIdBetweenDates(dateDetails).contains(roomId);
    }

    private BigDecimal calculateFinalPrice(
            RoomDetails roomDetails,
            List<AdditionalService> additionalServicesList,
            Optional<DiscountDetails> discountDetails,
            BookingCommand command) {
        long discountValue = discountDetails.map(discount -> 1 - discount.getValue() / 100).orElse(1);
        DateDetails dateDetails = command.getDateDetails();
        long period = Period.between(dateDetails.getStartDate(), dateDetails.getEndDate()).getDays();
        BigDecimal roomPrice = roomDetails.getStandardPrice();
        BigDecimal additionalServicesPrice;
        if(!additionalServicesList.isEmpty()) {
            additionalServicesPrice = additionalServicesList
                    .stream()
                    .map(AdditionalService::getPrice)
                    .reduce(ZERO, BigDecimal::add);
        } else {
            additionalServicesPrice = ZERO;
        }
        return roomPrice
                .add(additionalServicesPrice)
                .multiply(new BigDecimal(period))
                .multiply(new BigDecimal(discountValue));
    }

    private void checkBookingValidity(BookingStatus bookingStatus, BookingDate bookingDate) {
        switch(bookingStatus) {
            case CONFIRMED:
                throw new ApplicationException((ErrorCode.BOOKING_ALREADY_CONFIRMED));
            case CANCELLED:
                throw new ApplicationException(ErrorCode.BOOKING_ALREADY_CANCELLED);
            default:
                if(!isBookingDateValid(bookingDate)) {
                    throw new ApplicationException(ErrorCode.BOOKING_EXPIRED);
                }
        }
    }

    private void checkCustomersValidity(RoomDetails roomDetails, List<CustomerDetails> customerDetails) {
        if (customerDetails.size() > roomDetails.getBedAmount()) {
            throw new ApplicationException(ErrorCode.MAX_PEOPLE_EXCEEDED);
        }
    }

    private Optional<DiscountDetails> getDiscountDetails(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.of(discountsRepository.getDiscountDetails(code)
                .orElseThrow(() -> new ApplicationException(ErrorCode.DISCOUNT_CODE_NOT_FOUND)));
    }

    private boolean isBookingDateValid(BookingDate bookingDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate bookDate = LocalDate.from(bookingDate.getBookDate());
        LocalDate startDate = bookingDate.getStartDate();
        return currentDate.isBefore(startDate) && Period.between(bookDate, currentDate).getDays() <= 14;
    }
}
