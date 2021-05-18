package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageCommand {

    private Integer page;
    private Integer size;
    private String sortBy;
    private SortDir sortDir;

}
