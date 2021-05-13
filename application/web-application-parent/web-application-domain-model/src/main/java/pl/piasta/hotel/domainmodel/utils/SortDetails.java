package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SortDetails {

    private String sortBy;
    private SortDir sortDir;

}
