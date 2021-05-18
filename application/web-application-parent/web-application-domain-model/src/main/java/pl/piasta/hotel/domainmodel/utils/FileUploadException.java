package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;

@Getter
public class FileUploadException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.FILE_UPLOAD_COMMON;

    public FileUploadException() {
        super(errorCode);
    }
}
