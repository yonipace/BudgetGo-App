package app.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginCredentials {

	private String email;
	private String password;
}
