package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PageMeta {

    private final boolean isFirst;
    private final boolean isLast;
    private final boolean hasPrev;
    private final boolean hasNext;
    private final Integer currentPage;
    private final Integer totalPage;
    private final Long totalCount;
}
