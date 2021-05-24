package pl.piasta.hotel.domainmodel.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.domainmodel.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class RoomsPage {

    private final PageMeta meta;
    private final List<Room> content;
}
