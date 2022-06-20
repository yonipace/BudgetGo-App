package app.core.api.currency;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TargetCurrency {

	@JsonProperty("currency_name")
	private String currencyName;
	@JsonProperty("rate")
	private String rate;
	@JsonProperty("rate_for_amount")
	private String rateForAmount;

}
