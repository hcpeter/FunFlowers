package com.flexionmobile.client;

import com.flexionmobile.codingchallenge.integration.Purchase;
import com.flexionmobile.util.GsonCreator;
import com.flexionmobile.util.HttpClient;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationImplTest {

	private URIBuilder uriBuilder = new URIBuilder();
	private HttpClient httpClient = mock(HttpClient.class);
	private GsonCreator gsonCreator = new GsonCreator();
	private final HttpEntity entity = mock(HttpEntity.class);
	private CloseableHttpResponse response = mock(CloseableHttpResponse.class, RETURNS_DEEP_STUBS);


	@Test
	public void testBuyMethodSuccess() throws Exception {
		// given
		String jsonResponse = "{\"consumed\":\"false\",\"id\":\"c02cec9b-ce23-48df-8c12-2bba8f39ab66\",\"itemId\":\"item1\"}";
		when(entity.getContent()).thenReturn(new BufferedInputStream(new ByteArrayInputStream(jsonResponse.getBytes())));
		when(response.getEntity()).thenReturn(entity);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(response.getStatusLine().getReasonPhrase()).thenReturn("OK");
		when(httpClient.executeRequest(Matchers.any(URI.class), Matchers.eq(HttpMethods.POST))).thenReturn(response);
		IntegrationImpl integration = new IntegrationImpl(uriBuilder, httpClient, gsonCreator);

		// when
		Purchase item1 = integration.buy("item1");

		// then
		assertThat(uriBuilder.getPath(), is("/javachallenge/rest/developer/peter/buy/item1"));
		assertThat(item1.getId(), is("c02cec9b-ce23-48df-8c12-2bba8f39ab66"));
		assertThat(item1.getConsumed(), is(false));
		assertThat(item1.getItemId(), is("item1"));
	}
}