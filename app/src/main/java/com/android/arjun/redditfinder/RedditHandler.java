package com.android.arjun.redditfinder;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

class RedditHandler implements QuerySubmissionHandler<Submission> {
    private static final String TAG = RedditHandler.class.getSimpleName();

    private RestClient restClient;
    private User user;

    public RedditHandler(RestClient restClient, User user) {
        this.restClient = restClient;
        this.user = user;
    }

    void signIn(@NonNull RestClient restClient, @NonNull User user) {
        try {
            this.restClient = restClient;
            this.user = user;
            this.user.connect();
        } catch (Exception e) {
            Log.e(TAG, String.format("Failed to sign in user \"%s\". Exception: %s ", user.getUsername(), e));
        }
    }

    @Override
    @NonNull public List<Submission> submit(@NonNull final String query, @NonNull Integer limit) {
        if (limit < 0) throw new IllegalArgumentException();
        if (limit == 0) return unmodifiableList(Collections.<Submission>emptyList());

        final Submissions submissions = new Submissions(restClient, user);
        return unmodifiableList(submissions.ofSubreddit(query, SubmissionSort.TOP, -1, limit, null, null, true));
    }
}
