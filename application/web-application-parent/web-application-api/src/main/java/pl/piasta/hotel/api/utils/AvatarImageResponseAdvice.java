package pl.piasta.hotel.api.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pl.piasta.hotel.dto.users.AvatarImageResponse;

@ControllerAdvice
public class AvatarImageResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return AvatarImageResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @Nullable MethodParameter returnType,
            @Nullable MediaType selectedContentType,
            @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nullable ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        AvatarImageResponse avatarImageResponse = (AvatarImageResponse) body;
        setHeaders(response, avatarImageResponse);
        return avatarImageResponse.getData();
    }

    private void setHeaders(ServerHttpResponse response, AvatarImageResponse avatarImageResponse) {
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatarImageResponse.getName() + "\"");
        response.getHeaders().set(HttpHeaders.CONTENT_LENGTH, String.valueOf(avatarImageResponse.getData().length));
    }
}
