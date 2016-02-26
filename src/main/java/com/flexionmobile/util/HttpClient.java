package com.flexionmobile.util;

import org.apache.camel.component.http4.HttpMethods;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.net.URI;

/**
 * Created by tajthy on 2016.02.26..
 */
public interface HttpClient {
	CloseableHttpResponse executeRequest(URI uri, HttpMethods method) throws IOException;
}
