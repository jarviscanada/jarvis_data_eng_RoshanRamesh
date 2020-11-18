package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {
    private TwitterController controller;

    @Before
    public void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        //setup dependency
        HttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
                ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao dao = new TwitterDao(twitterHttpHelper);
        Service service = new TwitterService(dao);
        //pass dependency
        controller = new TwitterController(service);
    }

    @Test
    public void postTweet() throws Exception{
        String name = "@someone";
        String postTweet = "text" + name + System.currentTimeMillis();
        String[] args = {"Post", postTweet, "-44:80"};
        Double lat = -44.0;
        Double lon = 80.0;
        Tweet tweet = controller.postTweet(args);
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[1], 0);
        System.out.println(JsonParser.toJson(postTweet, true, false));
    }

    @Test
    public void showTweet() throws Exception{
        String name = "@someone";
        String postTweet = "some_text #abc " + name + System.currentTimeMillis();
        String[] args = {"Post", postTweet, "-44:80"};
        Double lat = -44.0;
        Double lon = 80.0;
        Tweet tweeted = controller.postTweet(args);
        String id = tweeted.getIdString();
        String[] argsArr = {"Show", id, postTweet};
        Tweet tweet = controller.showTweet(argsArr);

        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[1], 0);
        System.out.println(JsonParser.toJson(postTweet, true, false));
    }

    @Test
    public void deleteTweet() {
        String name = "@someone";
        String postTweet = "some_text #abc " + name + System.currentTimeMillis();
        String[] args = {"Post", postTweet, "-44:80"};
        Tweet tweeted = controller.postTweet(args);
        String id = tweeted.getIdString();
        String[] argsArr = {"Delete", id, postTweet};

        List<Tweet> deletedTweet = controller.deleteTweet(argsArr);
        assertEquals(tweeted.getText(), deletedTweet.get(0).getText());
    }
}