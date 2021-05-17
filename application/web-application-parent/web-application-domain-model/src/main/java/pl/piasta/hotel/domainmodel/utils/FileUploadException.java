package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;

@Getter
public class FileUploadException extends ApplicationException {

    public FileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
