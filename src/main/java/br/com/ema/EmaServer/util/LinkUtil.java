package br.com.ema.EmaServer.util;

public class LinkUtil {
    public static final String REL_COLLECTION = "collection";
    public static final String REL_NEXT = "next";
    public static final String REL_PREV = "prev";
    public static final String REL_FIRST = "first";
    public static final String REL_LAST = "last";

    private LinkUtil() {
        throw new AssertionError();
    }

    public static String createLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }
}
