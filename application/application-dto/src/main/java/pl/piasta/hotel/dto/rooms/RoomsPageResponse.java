package pl.piasta.hotel.dto.rooms;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.dto.utils.PageMeta;

import java.util.List;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class RoomsPageResponse {

    private PageMeta meta;
    private List<RoomResponse> content;
}
