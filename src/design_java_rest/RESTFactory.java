package design_java_rest;

public class RESTFactory {
    public static RESTResponseError make(RESTGeneralError define) {
        return new RESTResponseError(define);
    }

    public static RESTResponseSuccess make(RESTGeneralSuccess define) {
        return new RESTResponseSuccess(define);
    }
}
