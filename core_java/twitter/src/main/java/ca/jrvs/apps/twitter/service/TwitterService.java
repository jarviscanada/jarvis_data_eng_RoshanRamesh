package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {

        //Business logic:
        // e.g. text length, lat/lon range, id format
        validatePostTweet(tweet);

        //create tweet via dao
        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) {
        if (tweet == null) {
            throw new NullPointerException("Empty tweet");
        }
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException("Tweet overflow > 140");
        }

        Coordinates coordinates = tweet.getCoordinates();

        if (coordinates.getLongLat()[0] <= -180 || coordinates.getLongLat()[1] >= 180 || coordinates.getLongLat()[1] <= -90 || coordinates.getLongLat()[1] >= 90) {
            throw new RuntimeException("Invalid coordinates");
        }
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);
        return (Tweet) dao.findById(id);
    }

    private void validateId(String id) {
        if (!id.matches("^\\d+$")) {
            throw new RuntimeException("Invalid Tweet Id");
        }
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deleted = new ArrayList<>();

        for (String id : ids) {
            validateId(id);
            deleted.add((Tweet) dao.deleteById(id));
        }

        return deleted;
    }
}
