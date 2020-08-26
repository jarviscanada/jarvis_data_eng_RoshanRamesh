package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() {
        Tweet postTweet = TweetUtil.createTweet();
        when(service.postTweet(any())).thenReturn(postTweet);
        String[] args = {"Post", postTweet.getText(), "-44:80"};
        controller.postTweet(args);
    }

    @Test
    public void showTweet() {
        String name = "@someone";
        String postTweet = "text" + name + System.currentTimeMillis();
        String[] args = {"Post", postTweet, "-44:80"};
        Tweet newTweet = TweetUtil.createTweet();
        Tweet tweet = service.postTweet(newTweet);
        when(service.showTweet(anyString(), any())).thenReturn(new Tweet());
        Tweet showTweet = controller.showTweet(args);
        assertEquals(showTweet.getText(), tweet);
    }

    @Test
    public void deleteTweet() {
        List<Tweet> tweetList = new ArrayList<>();
        String name = "@someone";
        String postTweet = "text" + name + System.currentTimeMillis();
        String[] args = {"Post", postTweet, "-44:80"};
        Tweet newTweet = TweetUtil.createTweet();
        tweetList.add(newTweet);
        when(service.deleteTweets(any())).thenReturn(tweetList);
        tweetList = controller.deleteTweet(args);
        assertFalse(tweetList.isEmpty());
    }
}