package com.lastmans.swordpedia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("deprecation")
public class GameActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        WebView webView = findViewById(R.id.gameWeb);

        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        // Load the URL into the WebView
        webView.loadUrl("https://gandev21.github.io/sword/");
        webView.clearCache(true);
        webView.clearHistory();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}