package pl.piasta.hotel.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AvatarImageResponse {

    private String name;
    private String type;
    private byte[] data;
}
