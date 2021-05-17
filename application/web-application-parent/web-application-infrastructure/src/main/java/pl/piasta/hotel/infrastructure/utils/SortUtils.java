package pl.piasta.hotel.infrastructure.utils;

import org.springframework.data.domain.Sort;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

public final class SortUtils {

    public static Sort createSortProperty(SortProperties sortProperties) {
        String sortBy = sortProperties.getSortBy();
        Sort.Direction sortDir = Sort.Direction.valueOf(sortProperties.getSortDir().name());
        return Sort.by(sortDir, sortBy);
    }

    private SortUtils() {
    }
}
