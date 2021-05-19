package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PageProperties {

    private final Integer page;
    private final Integer size;
}
