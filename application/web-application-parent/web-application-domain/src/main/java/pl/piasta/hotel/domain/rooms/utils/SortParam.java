package pl.piasta.hotel.domain.rooms.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SortParam {

    private String sortBy;
    private SortDir sortDir;

}
