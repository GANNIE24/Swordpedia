package com.lastmans.swordpedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    public boolean backMain = false;
    public WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Retrieve link from Intent
        String link = getIntent().getStringExtra("link");

        // Initialize WebView
        webView = findViewById(R.id.showView);
        webView.getSettings().setJavaScriptEnabled(true);

        if (link != null && !link.isEmpty()) {
            webView.loadUrl(link);
        }

        // Set a WebViewClient to handle navigation within the WebView
        webView.setWebViewClient(new WebViewClient());
    }

    // Override onBackPressed to handle WebView navigation
    @Override
    public void onBackPressed() {
        if (backMain) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        this.backMain = true;
        Toast.makeText(this, "Please BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backMain = false;
            }
        }, 2000);
    }
}