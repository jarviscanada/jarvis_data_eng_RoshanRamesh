package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao dao;

    @Before
    public void setUp() {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        System.out.println(CONSUMER_KEY + "|" + CONSUMER_SECRET + "|" + ACCESS_TOKEN + "|" + TOKEN_SECRET);
        //setup dependency
        HttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
                ACCESS_TOKEN, TOKEN_SECRET);
        //pass dependency
        this.dao = new TwitterDao(twitterHttpHelper);
    }

    @Test
    public void create() throws Exception {
        String hashTag = "#abc";
        String text = "text" + hashTag + "" + System.currentTimeMillis();
        double lat = 1d;
        double lon = -1d;
        Tweet postTweet = TweetUtil.createTweet();
        System.out.println(JsonParser.toJson(postTweet, true, true));
        Tweet tweet = dao.create(postTweet);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[1], 0);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void findById() throws JsonProcessingException {
        String hashtag = "#abc";
        String text = "text" + hashtag + " " + System.currentTimeMillis();
        double lat = 1d;
        double lon = -1d;
        Tweet postTweet = TweetUtil.createTweet();
        System.out.println(JsonParser.toJson(postTweet, true, false));
        Tweet postedTweet = dao.create(postTweet);
        String id = postedTweet.getIdString();
        Tweet tweet = dao.findById(id);
        assertEquals(postTweet.getText(), tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getLongLat().length);
        assertEquals(lon, tweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, tweet.getCoordinates().getLongLat()[1], 0);
        assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void deleteById() {
        String hashtag = "#abc";
        String text = "text" + hashtag + " " + System.currentTimeMillis();
        double lat = 1d;
        double lon = -1d;
        Tweet postTweet = TweetUtil.createTweet();
        Tweet tweet = dao.create(postTweet);
        String id = tweet.getIdString();
        Tweet deletedTweet = dao.deleteById(id);
        assertEquals(postTweet.getText(), deletedTweet.getText());
        assertNotNull(deletedTweet.getCoordinates());
        assertEquals(2, deletedTweet.getCoordinates().getLongLat().length);
        assertEquals(lon, deletedTweet.getCoordinates().getLongLat()[0], 0);
        assertEquals(lat, deletedTweet.getCoordinates().getLongLat()[1], 0);
        assertTrue(hashtag.contains(deletedTweet.getEntities().getHashtags().get(0).getText()));
    }
}