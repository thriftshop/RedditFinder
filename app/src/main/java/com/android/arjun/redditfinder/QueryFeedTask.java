package com.android.arjun.redditfinder;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.jreddit.entity.Submission;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.android.arjun.redditfinder.FinderWithDisplayActivity.TAG;
import static java.util.Collections.unmodifiableList;


public class QueryFeedTask extends AsyncTask<String, ProgressBar, List<Submission>> {
    static final List<Submission> EMPTY_SUBMISSION_LIST = unmodifiableList(Collections.<Submission>emptyList());

    private final CompletionService<List<Submission>> completionService =
            new ExecutorCompletionService<List<Submission>>(Executors.newSingleThreadExecutor());

    private RedditHandler reddit;
    @NonNull private final ProgressBar progressBar;
    @NonNull private List<Submission> searchResults;

    public QueryFeedTask(@NonNull RedditHandler reddit, @NonNull ProgressBar progressBar) {
        this.reddit = reddit;
        this.progressBar = progressBar;
        searchResults = EMPTY_SUBMISSION_LIST;
    }

    @Override
    protected List<Submission> doInBackground(final String... queries) {
        if (queries == null) return EMPTY_SUBMISSION_LIST;

        completionService.submit(new Callable<List<Submission>>() {
            @Override
            public List<Submission> call() throws Exception {
                return reddit.submit(queries[0], 100);
            }
        });

        try {
            final Future<List<Submission>> completedFuture = completionService.take();
            searchResults = completedFuture.get();
        } catch (InterruptedException e) {
            searchResults = EMPTY_SUBMISSION_LIST;
            Log.e(TAG, String.format("Failed to search for query \"%s\". Exception: %s ", queries[0], e));
        } catch (ExecutionException e) {
            searchResults = EMPTY_SUBMISSION_LIST;
            Log.e(TAG, String.format("Failed to search for query \"%s\". Exception: %s ", queries[0], e));
        }
        return searchResults;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(ProgressBar... values) {
        onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Submission> submissions) {
        progressBar.setVisibility(View.GONE);
    }
}
