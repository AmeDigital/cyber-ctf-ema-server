package br.com.ema.EmaServer.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

public class AbstractController {

    protected static final String PAGE = "page";
    protected static final String SIZE = "size";
    protected static final int DEFAULT_SIZE = 50;

    protected final boolean hasNextPage(final int page, final int totalPages) {
        return page < (totalPages - 1);
    }

    protected final boolean hasPreviousPage(final int page) {
        return page > 0;
    }

    protected final boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }

    protected final boolean hasLastPage(final int page, final int totalPages) {
        return (totalPages > 1) && hasNextPage(page, totalPages);
    }

    protected final String constructNextPageUri(final HttpServletRequest request, final int page, final int size) {
        String nextPage = ServletUriComponentsBuilder.fromRequestUri(request)
                .replaceQueryParam(PAGE, page + 1)
                .queryParam(SIZE, size)
                .build()
                .encode()
                .toUriString();
        return nextPage;
    }

    protected final String constructFirstPageUri(HttpServletRequest request, int size) {
        String firstPage = ServletUriComponentsBuilder.fromRequestUri(request)
                .replaceQueryParam(PAGE, 0)
                .queryParam(SIZE, size)
                .build()
                .encode()
                .toUriString();
        return firstPage;
    }
}
