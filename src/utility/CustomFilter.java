package utility;

import com.googlecode.objectify.ObjectifyService;
import entity.RESTConstantHttp;
import entity.Student;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType(RESTConstantHttp.RESPONSE_CONTENT_TYPE);
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_METHODS,"GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Access-Control-Allow-Origin, Token");
        ObjectifyService.register(Student.class);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
