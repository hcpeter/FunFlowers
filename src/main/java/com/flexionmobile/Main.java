package com.flexionmobile;

import com.flexionmobile.client.IntegrationImpl;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import com.flexionmobile.util.GsonCreator;
import com.flexionmobile.util.HttpClientImpl;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * Main partition - wire up the whole application
 *
 */
public class Main {

    public static final String DEVELOPER_ID = "/peter";
    public static final String SCHEME = "http";
    public static final String HOST = "dev2.flexionmobile.com";

    public static void main( String[] args ) {
        URIBuilder baseUri = new URIBuilder().setScheme(SCHEME).setHost(HOST);
        IntegrationTestRunner testRunner = new IntegrationTestRunner();
        testRunner.runTests(new IntegrationImpl(baseUri, new HttpClientImpl(HttpClients.createDefault()), new GsonCreator()));
    }
}
