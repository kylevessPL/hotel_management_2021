package pl.piasta.hotel.api.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piasta.hotel.dto.utils.PageMeta;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpHeaders.LINK;

@ControllerAdvice
public class PageResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return PageResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @Nullable MethodParameter returnType,
            @Nullable MediaType selectedContentType,
            @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nullable ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        PageResponse<?> pageResponse = (PageResponse<?>) body;
        if (!pageResponse.getContent().isEmpty()) {
            setPaginationLinks(response, pageResponse.getPageMeta());
            setPaginationData(response, pageResponse);
        }
        return pageResponse.getContent();
    }

    private void setPaginationData(ServerHttpResponse response, PageResponse<?> pageResponse) {
        response.getHeaders().add("X-Count-Per-Page", String.valueOf(pageResponse.getPageSize()));
        response.getHeaders().add("X-Current-Page", String.valueOf(pageResponse.getPageMeta().getCurrentPage()));
        response.getHeaders().add("X-Total-Count", String.valueOf(pageResponse.getPageMeta().getTotalCount()));
        response.getHeaders().add("X-Total-Pages", String.valueOf(pageResponse.getPageMeta().getTotalPage()));
    }

    private void setPaginationLinks(ServerHttpResponse response, PageMeta pageMeta) {
        List<String> linksHeader = new LinkedList<>();
        if (!pageMeta.isFirst()) {
            String firstPage = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", 1)
                    .build()
                    .encode()
                    .toUriString();
            linksHeader.add("<" + firstPage + ">; rel=\"first\"");
        }
        if (pageMeta.isHasPrev()) {
            final String prevPage = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", pageMeta.getCurrentPage() - 1)
                    .build()
                    .encode()
                    .toUriString();
            linksHeader.add("<" + prevPage + ">; rel=\"prev\"");
        }
        if (pageMeta.isHasNext()) {
            final String nextPage = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", pageMeta.getCurrentPage() + 1)
                    .build()
                    .encode()
                    .toUriString();
            linksHeader.add("<" + nextPage + ">; rel=\"next\"");
        }
        if (!pageMeta.isLast()) {
            final String lastPage = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", pageMeta.getTotalPage())
                    .build()
                    .encode()
                    .toUriString();
            linksHeader.add("<" + lastPage + ">; rel=\"last\"");
        }
        if (!linksHeader.isEmpty()) {
            response.getHeaders().add(LINK, String.join(", ", linksHeader));
        }
    }
}
