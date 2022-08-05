package app.core.api.currency;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ApiRequest {

	static HttpResponse<String> response = Unirest
			.get("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=USD")
			.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
			.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21").asString();

	public static void main(String[] args) {

		System.out.println(response);
		System.out.println(response.getBody());

	}
}
