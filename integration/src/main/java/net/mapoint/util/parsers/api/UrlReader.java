package net.mapoint.util.parsers.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UrlReader {

    private static final Logger LOGGER = LogManager.getLogger(UrlReader.class);

    public static String readUrl(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("accept", "application/json");
        String output = null;
        try {
            response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                LOGGER.warn("Unexpected HTTP code: " + response.getStatusLine().getStatusCode() + "by reading url");
                throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
            }
            output = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            LOGGER.error("Error by getting response", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                    httpClient.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error by closing resources", e);
            }
        }
        return output;
    }
}