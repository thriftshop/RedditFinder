package com.android.arjun.redditfinder;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.PoliteHttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests the capabilities of RedditHandler class. */
public class RedditHandlerTest {
    private final String query = "test";
    private final String password = "PASSWORD";
    private final int MAXIMUM_ENTRIES = 5;

    RestClient restClient;
    User user;

    private RedditHandler subject;
    private List<Submission> results;

    @Before
    public void setUp() {
        restClient = new PoliteHttpRestClient();
        restClient.setUserAgent("bot/1.0 by name");

        user = new User(restClient, "USER", "PASSWORD");

        subject = new RedditHandler(restClient, user);
    }

    @Test
    public void shouldNotExceedMaximumSubmissionsEntries() {
        // when
        results = subject.submit(query, MAXIMUM_ENTRIES);

        // then
        assertThat(results.size()).isLessThanOrEqualTo(5);
    }

    @Test
    public void shouldReturnEmptyListWhenSearchLimitIsZero() {
        // when
        results = subject.submit(query, 0);

        // then
        assertThat(results).isEmpty();
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSearchLimitIsLessThanZero() {
        results = subject.submit(query, -5);
    }
}