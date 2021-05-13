package pl.piasta.hotel.domainmodel.rooms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.utils.SortDetails;

@NoArgsConstructor
@Getter
@Setter
public class RoomCommand {

    private DateDetails dateDetails;
    private SortDetails sortDetails;

}
