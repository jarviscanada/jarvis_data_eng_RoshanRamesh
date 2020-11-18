package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com/1.1/statuses/";
    private static final String POST_PATH = "update.json?status=";
    private static final String SHOW_PATH = "show.json?id=";
    private static final String DELETE_PATH = "destroy/";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response Code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;
 //   private JsonParser JsonUtil;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet) {
        //Construct URI
        URI uri;
        try {
            uri = getPostURI(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input: " , e);
        }

        //Execute HTTP Request
        HttpResponse response;
        response = httpHelper.httpPost(uri);

        //Validate response and send response to Tweet object
        return parseResponseBody(response);
    }

    /**
     * check response status code convert response entity to tweet
     */

    protected Tweet parseResponseBody(HttpResponse response) {
        Tweet tweet = null;

        //Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
                throw new RuntimeException("Unexpected HTTP status:" + status);
            }

        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }
            //Convert Response Entity to string
            String jsonStr;
            try {
                jsonStr=EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert entity to string " + e);
            }

            //CDeserialize JSON String to Tweet object
            try {
                tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
            } catch (IOException e) {
                throw new RuntimeException("Unable to convert JSON str to Object ", e);
            }
        return tweet;
    }

    @Override
    public Tweet findById(String id) {
        URI uri;
        try {
            uri = getFindURI(id);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI syntax for tweet id", e);
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response);
    }

    @Override
    public Tweet deleteById(String id) {
        URI uri;
        try {
            uri = getDeleteUri(id);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI syntax for tweet id", e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response);

    }

    private URI getPostURI(Tweet tweet) throws URISyntaxException {
        String text = tweet.getText();
        float[] coordinates = tweet.getCoordinates().getLongLat();
        double longitude = coordinates[0];
        double latitude = coordinates[1];

        PercentEscaper percentEscaper = new PercentEscaper("", false);
        return new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL +
                percentEscaper.escape(text) + AMPERSAND + "long" + EQUAL + longitude +
                AMPERSAND + "lat" + EQUAL + latitude);
    }

    private URI getFindURI(String id) throws URISyntaxException {
        return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);
    }

    private URI getDeleteUri(String id) throws URISyntaxException {
        return new URI(API_BASE_URI + DELETE_PATH + "/" + id + ".json");
    }
}