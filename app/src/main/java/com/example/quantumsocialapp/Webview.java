package com.example.quantumsocialapp;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantumsocialapp.databinding.ActivityWebviewBinding;



public class Webview extends AppCompatActivity {

    ActivityWebviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.loadUrl(url);

    }
}