package design_java_rest;

import design_java_rest.entity.RESTConstantHttp;
import design_java_rest.util.RESTStringUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class RESTHandle {
    /**
     * This function is a beginner of REST Api, all of request must be handled
     * by this function. It checks header parameters that required, be sure that
     * request are made from real client.
     *
     * @param resp
     *            HttpServletResponse response, to make a response to client.
     * @param acceptMethods
     *            list of methods that api accept from client.
     */
    public static void doOption(HttpServletResponse resp, ArrayList<String> acceptMethods) {
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_METHODS,
                RESTStringUtil.joinCollectionString(acceptMethods, ","));
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_HEADERS,
                RESTConstantHttp.HEADER_CONTENT_TYPE + ", " + RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN + " ," + RESTConstantHttp.HEADER_AUTHORIZATION);
    }

    /**
     * After doOption() method, developer must call this function if client is
     * identified.
     *
     * @param resp
     *            HttpServletResponse response, to client.
     */
    public static void passRequest(HttpServletResponse resp, ArrayList<String> acceptMethods) {
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_METHODS,
                RESTStringUtil.joinCollectionString(acceptMethods, ","));
    }

}
