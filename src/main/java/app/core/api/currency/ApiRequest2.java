package app.core.api.currency;

import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

public class ApiRequest2 {

//	static HttpResponse<?> response = Unirest
//			.get("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=USD&to=EUR&amount=1")
//			.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
//			.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21").asObject(Object.class);

	public static void main(String[] args) {

		ObjectMapper objectMapper;

//		BaseCurrency baseCurrency = Unirest
//				.get("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=USD")
//				.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
//				.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21").asJson();

		System.out
				.println(Unirest.get("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=USD")
						.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
						.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21"));

//		System.out.println(baseCurrency);

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
