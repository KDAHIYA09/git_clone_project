package com.example.gitclone.activities

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gitclone.R

class project_url_webview : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_project_url_webview)

        val webView = findViewById<WebView>(R.id.myWeb)
        val projectUrl = intent.getStringExtra("PROJECT_URL")

        if (projectUrl != null) {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(projectUrl)
        }

    }
}