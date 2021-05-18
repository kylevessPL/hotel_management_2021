package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class SortProperties {

    private final String sortBy;
    private final SortDir sortDir;
}
