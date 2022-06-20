
package app.core.api.currency;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
public class Rates {

	@JsonIgnore
	private HashMap<String, TargetCurrency> additionalProperties = new HashMap<String, TargetCurrency>();

	@JsonAnyGetter
	public HashMap<String, TargetCurrency> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, TargetCurrency value) {
		this.additionalProperties.put(name, value);
	}

}
