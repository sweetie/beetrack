package beetrack.com.news.mvp.view.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import beetrack.com.news.R;
import beetrack.com.news.mvp.common.interfaces.Listener.ExecutorListener;
import beetrack.com.news.mvp.common.managers.FutureTaskManager;


/**
 * Created by Enny Querales
 */
public class WebViewActivity
        extends AppCompatActivity
        implements ExecutorListener {


    /**
     * Attributes
     */
    private RelativeLayout relativeLayoutDialog;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = getIntent().getStringExtra("url");

        initializeViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initializeViews() {

        relativeLayoutDialog = (RelativeLayout) findViewById(R.id.linear_dialog_dialog);
        WebView webView = (WebView) findViewById(R.id.web_view);
        relativeLayoutDialog.setVisibility(View.VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        // WebView set up
        WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(settings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new AppBrowser(this));
        webView.loadUrl(url);
    }

    @Override
    public void execute(String name) {
        relativeLayoutDialog.setVisibility(View.GONE);
    }


    public class AppBrowser extends WebViewClient {
        private ExecutorListener listener;

        public AppBrowser(ExecutorListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            FutureTaskManager.executeAfter(listener, "hide", 5, false);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
