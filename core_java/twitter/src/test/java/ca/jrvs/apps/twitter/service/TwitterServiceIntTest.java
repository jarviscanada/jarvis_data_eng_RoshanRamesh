package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService service;

    @Before
    public void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        HttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
                ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao dao = new TwitterDao(twitterHttpHelper);
        service = new TwitterService(dao);
    }

    @Test
    public void postTweet() throws Exception {
        Tweet postTweet = TweetUtil.createTweet();
        String hashTag = "abc";
        String text = "text" + hashTag + "" + System.currentTimeMillis();
        String name = "@someone";
        double lat = -44;
        double lon = 80;
        System.out.println(JsonParser.toJson(postTweet, true, false));
        Tweet tweet = service.postTweet(postTweet);

        assertEquals(postTweet.getText(), tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[1], 0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
        System.out.println(JsonParser.toJson(postTweet, true, false));
    }

    @Test
    public void showTweet() throws JsonProcessingException {
        String hashTag = "#abc";
        String text = "text" + hashTag + "" + System.currentTimeMillis();
        Double lat = -44.0;
        Double lon = 80.0;
        Tweet postTweet = TweetUtil.createTweet();
        Tweet postedTweet = service.postTweet(postTweet);
        String id = postedTweet.getIdString();

        Tweet tweet = service.showTweet(id, null);

        assertEquals(postTweet.getText(), tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[1], 0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
        System.out.println(JsonParser.toJson(tweet, true, false));
    }

    @Test
    public void deleteTweets() {
        Tweet postTweet = TweetUtil.createTweet();
        Tweet tweet = service.postTweet(postTweet);
        String id = tweet.getIdString();
        String[] Strid = {id};
        List<Tweet> deletedTweet = service.deleteTweets(Strid);
        assertEquals(tweet.getText(), deletedTweet.get(0).getText());
    }
}