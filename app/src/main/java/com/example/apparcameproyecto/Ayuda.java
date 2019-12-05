package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Ayuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        WebView webView = findViewById(R.id.ayuda);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://apparcame.000webhostapp.com/apparcamebd/Ayuda.html");
    }
}
