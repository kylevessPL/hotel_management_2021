package pl.piasta.hotel.domainmodel.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.domainmodel.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class UsersPage {

    private final PageMeta meta;
    private final List<PagedUserInfo> content;
}
