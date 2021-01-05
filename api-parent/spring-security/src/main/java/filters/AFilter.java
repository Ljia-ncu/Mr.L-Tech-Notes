package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName: AFilter
 * @Description
 * @Author Mr.L
 * @Date 2020/12/2 23:02
 * @Version 1.0
 */
public class AFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("A Filter");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
