package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

    public static Tweet createTweet() {
        String name = "@someone";
        String text = "text" + name + System.currentTimeMillis();
        float[] longLat = {-44, 80};
        Coordinates coordinates = new Coordinates(longLat, "Point");
        return new Tweet(text, coordinates);
        //return new Tweet();
    }

}
