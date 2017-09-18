package com.android.arjun.redditfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FinderActivity extends AppCompatActivity {
    @Bind(R.id.finder_search_bar) SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) { return true; }

            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.isEmpty()) return true;

                passQueryToNextActivity(query);
                return true;
            }
        };
        searchBar.setOnQueryTextListener(queryTextListener);
    }

    private void passQueryToNextActivity(@NonNull final String query) {
        final Intent child = new Intent(FinderActivity.this, FinderWithDisplayActivity.class);
        child.putExtra("searchedText", query);
        startActivity(child);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchBar.setQuery("", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchBar.setQuery("", false);
    }
}
