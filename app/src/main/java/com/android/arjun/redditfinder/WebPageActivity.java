package com.android.arjun.redditfinder;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WebPageActivity extends AppCompatActivity {
    @Bind(R.id.submission_web_page_view) WebView webPage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submission_web_page);

        ButterKnife.bind(this);

        webPage.setWebViewClient(new WebPageBrowser());
        if(!getIntent().hasExtra("url")) return;
        final String url = getIntent().getExtras().getString("url");
        if (url != null) {
            webPage.getSettings().setLoadsImagesAutomatically(true);
            webPage.getSettings().setJavaScriptEnabled(true);
            webPage.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webPage.loadUrl(url);
        } else {
            Toast.makeText(this, "Unable to open the web page.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private class WebPageBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(String.valueOf(request.getUrl()));
            }
            return super.shouldOverrideUrlLoading(view, request);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                view.loadUrl(url);
            return true;
        }
    }
}
