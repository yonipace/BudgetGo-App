package app.core.api.currency;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.core.entities.ExchangeRate.Currency;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class RestClient {

	static String baseUrl = "https://currency-converter5.p.rapidapi.com/currency/convert";

	static Map<String, Object> map = new HashMap<String, Object>();

	public static void main(String[] args) {

		Currency[] Currencies = Currency.values();

		System.out.println(Arrays.asList(Currencies));

		GetRequest request = Unirest.get(baseUrl).queryString("from", "USD")
				.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
				.header("x-rapidapi-key", "0e72af5013msh66d35c4001964d7p1a8d68jsndc5983152a21")
				.queryString("to", Arrays.asList(Currencies));

		HttpResponse<String> response = request.asString();

		System.out.println(response.getBody());

	}
}
