package com.example.vahan_app

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)

        val url = intent.getStringExtra("url")

        if (url != null) {
            webView.settings.javaScriptEnabled = true // Enable JavaScript if needed
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        } else {
            // Handle the case where the URL is not available
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
