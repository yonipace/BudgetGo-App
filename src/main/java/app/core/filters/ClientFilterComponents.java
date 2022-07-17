package app.core.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import app.core.auth.JwtUtil;

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
		filterRegistrationBean.addUrlPatterns("/user/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<UserFilter> tripFilter(JwtUtil jwtUtil) {

		FilterRegistrationBean<UserFilter> filterRegistrationBean;

		filterRegistrationBean = new FilterRegistrationBean<>(new UserFilter(jwtUtil));
		filterRegistrationBean.addUrlPatterns("/trip/*");
		return filterRegistrationBean;
	}
}
