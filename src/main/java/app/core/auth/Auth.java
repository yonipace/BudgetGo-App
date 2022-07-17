package app.core.auth;

import app.core.auth.JwtUtil.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auth {

	private String email;
	private ClientType client;
	private String token;
	private String firstName;
	private String lastName;

}
