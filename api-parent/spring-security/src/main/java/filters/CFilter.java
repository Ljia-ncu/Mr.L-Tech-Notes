package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName: CFilter
 * @Description
 * @Author Mr.L
 * @Date 2020/12/2 23:09
 * @Version 1.0
 */
public class CFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("C Filter");
        String target = servletRequest.getParameter("name");
        if ("c".equals(target)) {
            System.out.println("return -> C");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}