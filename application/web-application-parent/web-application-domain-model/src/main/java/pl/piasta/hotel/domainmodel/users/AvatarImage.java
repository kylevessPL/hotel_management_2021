package pl.piasta.hotel.domainmodel.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class AvatarImage {

    private final String name;
    private final String type;
    private final byte[] data;
}
