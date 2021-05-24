package pl.piasta.hotel.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.piasta.hotel.domain.bookings.BookingsService;
import pl.piasta.hotel.domain.security.UserDetailsImpl;

@Component
@RequiredArgsConstructor
public class SecurityControl {

    private final BookingsService bookingsService;

    public boolean hasBookingResourcePermission(Integer bookingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return bookingsService.hasPermission(userId, bookingId);
    }
}
