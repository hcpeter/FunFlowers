package com.flexionmobile.client;

import com.flexionmobile.codingchallenge.integration.Purchase;
import com.flexionmobile.pojos.PurchaseImpl;
import com.flexionmobile.util.GsonCreator;
import com.flexionmobile.util.HttpClient;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class IntegrationImplTest {

    private static final String BASE_URL = "/javachallenge/rest/developer/peter";
    private final HttpEntity entity = mock(HttpEntity.class);
    private URIBuilder uriBuilder = new URIBuilder();
    private HttpClient httpClient = mock(HttpClient.class);
	private GsonCreator gsonCreator = new GsonCreator();
    private final IntegrationImpl integration = new IntegrationImpl(uriBuilder, httpClient, gsonCreator);
    private CloseableHttpResponse response = mock(CloseableHttpResponse.class, RETURNS_DEEP_STUBS);

    @Test
    public void testBuyMethod() throws Exception {
        // given
        createMockResponseFromString("{\"consumed\":\"false\",\"id\":\"c02cec9b-ce23-48df-8c12-2bba8f39ab66\",\"itemId\":\"item1\"}");
        setStatusResponse(HttpStatus.SC_OK, "OK");
        when(httpClient.executeRequest(Matchers.any(URI.class), Matchers.eq(HttpMethods.POST))).thenReturn(response);

        // when
        Purchase item1 = integration.buy("item1");

		// then
        assertThat(uriBuilder.getPath(), is(BASE_URL + "/buy/item1"));
        assertItem(item1, "c02cec9b-ce23-48df-8c12-2bba8f39ab66", false, "item1");
    }

    @Test
    public void testGetPurchases() throws Exception {
        // given
        createMockResponseFromString("{\"purchases\":[{\"consumed\":\"true\",\"id\":\"3ca29403-921c-48eb-867c-ed210b0b5baf\",\"itemId\":\"item1\"},{\"consumed\":\"true\",\"id\":\"4b93084c-704f-431c-aed2-4e32188220a0\",\"itemId\":\"item1\"},{\"consumed\":\"true\",\"id\":\"0682ab3d-fed1-4ae4-9f54-5af83f36da21\",\"itemId\":\"item1\"}]}");
        setStatusResponse(HttpStatus.SC_OK, "OK");
        when(httpClient.executeRequest(Matchers.any(URI.class), Matchers.eq(HttpMethods.GET))).thenReturn(response);

        // when
        List<Purchase> purchases = integration.getPurchases();

        // then
        assertThat(uriBuilder.getPath(), is(BASE_URL + "/all"));
        assertThat("Purchases should contains 3 items", purchases.size(), is(3));
        assertItem(purchases.get(0), "3ca29403-921c-48eb-867c-ed210b0b5baf", true, "item1");
    }

    @Test
    public void testConsume() throws Exception {
        // given
        Purchase purchase = new PurchaseImpl("id", false, "itemId");
        createMockResponseFromString("");
        setStatusResponse(HttpStatus.SC_OK, "OK");
        when(httpClient.executeRequest(Matchers.any(URI.class), Matchers.eq(HttpMethods.POST))).thenReturn(response);

        // when
        integration.consume(purchase);

        // then
        assertThat(uriBuilder.getPath(), is(BASE_URL + "/consume/id"));

    }

    private void createMockResponseFromString(String jsonResponse) throws IOException {
        when(entity.getContent()).thenReturn(new BufferedInputStream(new ByteArrayInputStream(jsonResponse.getBytes())));
        when(response.getEntity()).thenReturn(entity);
    }

    private void setStatusResponse(int statusCode, String responseString) {
        when(response.getStatusLine().getStatusCode()).thenReturn(statusCode);
        when(response.getStatusLine().getReasonPhrase()).thenReturn(responseString);
    }

    private void assertItem(Purchase item1, String expectedItemId, boolean expectedConsumed, String expectedItemName) {
        assertThat(item1.getId(), is(expectedItemId));
        assertThat(item1.getConsumed(), is(expectedConsumed));
        assertThat(item1.getItemId(), is(expectedItemName));
    }

}