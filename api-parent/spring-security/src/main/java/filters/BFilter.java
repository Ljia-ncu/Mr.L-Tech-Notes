package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName: BFilter
 * @Description
 * @Author Mr.L
 * @Date 2020/12/2 23:08
 * @Version 1.0
 */
public class BFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("B Filter");
        String target = servletRequest.getParameter("name");
        if ("b".equals(target)) {
            System.out.println("return -> B");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
