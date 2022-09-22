package app.core.filters;

import app.core.auth.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClientFilterComponents {

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter(JwtUtil jwtUtil) {

        FilterRegistrationBean<AdminFilter> filterRegistrationBean;

        filterRegistrationBean = new FilterRegistrationBean<AdminFilter>(new AdminFilter(jwtUtil));
        filterRegistrationBean.addUrlPatterns("/admin/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserFilter> userFilter(JwtUtil jwtUtil) {

        FilterRegistrationBean<UserFilter> filterRegistrationBean;

        filterRegistrationBean = new FilterRegistrationBean<>(new UserFilter(jwtUtil));
        filterRegistrationBean.addUrlPatterns("/trip/*", "/expense/*", "/excel/*");
        return filterRegistrationBean;
    }

    //    @Bean
//    public FilterRegistrationBean<UserFilter> tripFilter(JwtUtil jwtUtil) {
//
//        FilterRegistrationBean<UserFilter> filterRegistrationBean;
//
//        filterRegistrationBean = new FilterRegistrationBean<>(new UserFilter(jwtUtil));
//        filterRegistrationBean.addUrlPatterns("/trip/*");
//        return filterRegistrationBean;
//    }
    @Bean
    public FilterRegistrationBean<DemoCORSFilter> demoCORSFilterFilter() {

        FilterRegistrationBean<DemoCORSFilter> filterRegistrationBean;

        filterRegistrationBean = new FilterRegistrationBean<>(new DemoCORSFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
