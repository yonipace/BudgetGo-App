package app.core.api.currency;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ApiRequest {

	static HttpResponse<String> response = Unirest
			.get("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=USD&to=EUR&amount=1")
			.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
			.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21").asString();

	public static void main(String[] args) {

		System.out.println(response.getClass());
		System.out.println(response.getBody().getClass());

//
//		try {
//			System.out.println(response.getBody().getClass());
//			System.out.println("=========================");
//
//			BaseCurrency currency = objectMapper.readValue(response.getBody().toString(), BaseCurrency.class);
//			System.out.println(currency);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

	}
}
