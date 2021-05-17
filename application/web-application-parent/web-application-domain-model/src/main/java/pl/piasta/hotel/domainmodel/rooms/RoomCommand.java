package pl.piasta.hotel.domainmodel.rooms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

@NoArgsConstructor
@Getter
@Setter
public class RoomCommand {

    private DateDetails dateDetails;
    private SortProperties sortProperties;

}
