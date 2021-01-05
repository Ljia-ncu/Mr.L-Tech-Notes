package filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @ClassName: FilterConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/2 23:08
 * @Version 1.0
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> buildFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Filter> buildFilter2() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new BFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Filter> buildFilter3() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CFilter());
        bean.setOrder(3);
        bean.addUrlPatterns("/*");
        return bean;
    }
}
