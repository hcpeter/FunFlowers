package com.flexionmobile.util;

import com.flexionmobile.codingchallenge.integration.Purchase;
import com.google.gson.*;

/**
 * Builds a Gson parser
 */
public class GsonCreator {

	public Gson createGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Purchase.class, new PurchaseDeserializer());
		return gsonBuilder.create();
	}
}
