package utility;

import com.googlecode.objectify.ObjectifyService;
import entity.Student;

import javax.servlet.*;
import java.io.IOException;

public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ObjectifyService.register(Student.class);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
