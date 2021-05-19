package pl.piasta.hotel.domainmodel.rooms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class DateDetails {

    private LocalDate startDate;
    private LocalDate endDate;
}
