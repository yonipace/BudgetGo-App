
package app.core.api.currency;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "base_currency_code", "base_currency_name", "amount", "updated_date", "rates", "status" })
public class BaseCurrency {

	@JsonProperty("base_currency_code")
	public String baseCurrencyCode;
	@JsonProperty("base_currency_name")
	public String baseCurrencyName;
	@JsonProperty("amount")
	public String amount;
	@JsonProperty("updated_date")
	public String updatedDate;
	@JsonProperty("rates")
	public Rates rates;
	@JsonProperty("status")
	public String status;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Currency [baseCurrencyCode=" + baseCurrencyCode + ", baseCurrencyName=" + baseCurrencyName + ", amount="
				+ amount + ", updatedDate=" + updatedDate + ", rates=" + rates + ", status=" + status
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
