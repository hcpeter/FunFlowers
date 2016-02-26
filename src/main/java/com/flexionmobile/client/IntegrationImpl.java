package com.flexionmobile.client;

import com.flexionmobile.Main;
import com.flexionmobile.pojos.Purchases;
import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.Purchase;
import com.flexionmobile.util.GsonCreator;
import com.flexionmobile.util.HttpClient;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Client implementation for {@link Integration}
 */
public class IntegrationImpl implements Integration {

    private final static Logger LOG = Logger.getLogger(IntegrationImpl.class);
    public static final String BASE_PATH = "/javachallenge/rest/developer";
    public static final String DEVELOPER_ID = "developerId";
    public static final String ITEM_ID = "itemId";
    private URIBuilder baseUri;
    private HttpClient httpClient;
    private GsonCreator gsonCreator;

    public IntegrationImpl(URIBuilder baseUri, HttpClient HttpClient, GsonCreator gsonCreator) {
        this.baseUri = baseUri;
        this.httpClient = HttpClient;
        this.gsonCreator = gsonCreator;
    }

    /**
     * Buy an itemId
     * @param itemId itemId
     * @return the {@link Purchase}d item
     */
    public Purchase buy(String itemId) {
        try {
            URI postBuyUri = baseUri.setPath(BASE_PATH + Main.DEVELOPER_ID + "/buy/" + itemId)
				.setParameter(DEVELOPER_ID, Main.DEVELOPER_ID).setParameter(ITEM_ID, itemId).build();
            CloseableHttpResponse response = httpClient.executeRequest(postBuyUri, HttpMethods.POST);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            logResponseStatus("Buy: ", response);
            return gsonCreator.createGsonBuilder().fromJson(jsonResponse, Purchase.class);
        } catch (URISyntaxException | IOException e) {
            LOG.error("Invocation error: buy method" + e);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<Purchase> getPurchases() {
        try {
            URI getPurchasesUri = baseUri.setPath(BASE_PATH + Main.DEVELOPER_ID + "/all").setParameter(DEVELOPER_ID, Main.DEVELOPER_ID).build();
            CloseableHttpResponse response = httpClient.executeRequest(getPurchasesUri, HttpMethods.GET);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            logResponseStatus("GetPurchases: ", response);
            return gsonCreator.createGsonBuilder().fromJson(jsonResponse, Purchases.class).getPurchases();
        } catch (URISyntaxException | IOException e) {
            LOG.error("Invocation error: getPurchases method" + e);
        }
        return null;
    }

    /**
     *
     * @param purchase
     */
    public void consume(Purchase purchase) {
        try {
            URI postConsumeUri = baseUri.setPath(BASE_PATH + Main.DEVELOPER_ID + "/consume/" + purchase.getId()).build();
            CloseableHttpResponse response = httpClient.executeRequest(postConsumeUri, HttpMethods.POST);
            logResponseStatus("Consume: ", response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void logResponseStatus(String message, CloseableHttpResponse response) {
        LOG.info("[" + message + "] " + String.valueOf(response.getStatusLine().getStatusCode()) + " - " + response.getStatusLine().getReasonPhrase());
    }
}
