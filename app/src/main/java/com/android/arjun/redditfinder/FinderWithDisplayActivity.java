package com.android.arjun.redditfinder;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.PoliteHttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.android.arjun.redditfinder.QueryFeedTask.EMPTY_SUBMISSION_LIST;

public class FinderWithDisplayActivity extends AppCompatActivity {
    public static final String TAG = FinderActivity.class.getSimpleName();

    @NonNull private static final String DEFAULT_USERNAME = "USER";
    @NonNull private static final String DEFAULT_PASSWORD = "PASSWORD";

    @Bind(R.id.searched_text_view) TextView searchedTextView;
    @Bind(R.id.search_result_list) ListView searchResultsView;
    @Bind(R.id.display_results_search_bar) SearchView searchBar;
    @Bind(R.id.display_results_progress_bar) ProgressBar progressBar;

    @NonNull private List<Submission> searchResults = new ArrayList<Submission>();

    private QueryFeedTask queryService;
    private SubmissionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_results);

        ButterKnife.bind(this);

        if(!getIntent().hasExtra("searchedText")) return;
        final String query = getIntent().getExtras().getString("searchedText");
        queryService = new QueryFeedTask(getDefaultRedditCommunicator(), progressBar);

        searchResults.clear();
        searchResults.addAll(performSearchFor(query));
        emitToast(searchResults);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) { return true; }

            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.isEmpty()) return true;

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (NullPointerException e) {
                    Log.e(TAG, "Failed to hide soft keyboard. Exception: " + e);
                }

                searchResults.clear();
                searchResults.addAll(performSearchFor(query));
                adapter.notifyDataSetChanged();
                emitToast(searchResults);
                return true;
            }
        };
        searchBar.setOnQueryTextListener(queryTextListener);

        adapter = new SubmissionAdapter(this, R.layout.submission_row, R.id.question, searchResults);
        searchResultsView.setAdapter(adapter);

    }

    private void emitToast(@NonNull List<Submission> searchResults) {
        Toast.makeText(this,
                "Search results " + (searchResults.isEmpty() ? "zero... Try a different word" : "size: " + searchResults.size()),
                Toast.LENGTH_LONG)
                .show();
    }

    private List<Submission> performSearchFor(final String query) {
        if (query == null) return EMPTY_SUBMISSION_LIST;
        searchedTextView.setText(getResources().getString(R.string.searched_results) + " \"" + query + "\"");
        return queryService.doInBackground(query);
    }


    @NonNull
    private RedditHandler getDefaultRedditCommunicator() {
        return getRedditCommunicator(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    @NonNull
    private RedditHandler getRedditCommunicator(@NonNull String userName, @NonNull String password) {
        final RestClient restClient = new PoliteHttpRestClient();
        restClient.setUserAgent("bot/1.0 by name");

        final User user = new User(restClient, userName, password);
        final RedditHandler reddit = new RedditHandler(restClient, user);
        reddit.signIn(restClient, user);
        return reddit;
    }
}
