package pl.piasta.hotel.api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.dto.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PageResponse<T> {

    private final int pageSize;
    private final PageMeta pageMeta;
    private final List<T> content;
}
