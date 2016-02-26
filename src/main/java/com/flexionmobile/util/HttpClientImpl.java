package com.flexionmobile.util;

import org.apache.camel.component.http4.HttpMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;

/**
 * Creates a {@link CloseableHttpClient}
 */
public class HttpClientImpl implements HttpClient {

	private CloseableHttpClient httpClient;

	public HttpClientImpl(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * Execute requests based on {@link HttpMethods} type
	 * @param uri request's uri
	 * @param method http method
	 * @return request's response
	 * @throws IOException
	 */
	@Override
	public CloseableHttpResponse executeRequest(URI uri, HttpMethods method) throws IOException {
		CloseableHttpResponse response = null;
		switch (method){
			case GET:
				response = httpClient.execute(new HttpGet(uri));
				break;
			case POST:
				response = httpClient.execute(new HttpPost(uri));
		}
		return response;
	}
}
