package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.auth.JwtUtil;
import app.core.auth.JwtUtil.ClientType;

public class UserFilter implements Filter {

	private JwtUtil jwtUtil;

	public UserFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String token = request.getHeader("token");

		try {

			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST,DELETE");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type,my-token,myHeaders,token");

			if (request.getMethod().equals("OPTIONS")) {
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}

			if (jwtUtil.extractClientType(token).equals(ClientType.USER)) {
				chain.doFilter(request, response);
			} else {
				// if not logged in - block the request
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");
			}

		} catch (Exception e) {
			e.printStackTrace();

			response.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in from filter");

		}
	}
}