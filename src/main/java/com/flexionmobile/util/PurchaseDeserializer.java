package com.flexionmobile.util;

import com.flexionmobile.codingchallenge.integration.Purchase;
import com.flexionmobile.pojos.PurchaseImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Deserialize {@link Purchase} objects
 */
public class PurchaseDeserializer implements JsonDeserializer<Purchase> {

	@Override
	public Purchase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
		throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		return new PurchaseImpl(
			jsonObject.get("id").getAsString(),
			jsonObject.get("consumed").getAsBoolean(),
			jsonObject.get("itemId").getAsString()
		);
	}
}
