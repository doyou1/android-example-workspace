package com.example.qnaproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private val tag = "WebViewActivity"
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        Log.e(tag, "intent.data: ${intent.data}")

        val uri = intent.data
        uri?.let {
            val webViewType = it.getQueryParameter("type").toString()
            var url = ""
            var title = ""

            if (webViewType == "privacy") {
                url = "https://www.jamjami.co.kr/mobile/privacy.html"
                title = "개인정보 처리방침"
            } else if (webViewType == "terms") {
                url = "https://www.jamjami.co.kr/mobile/terms.html"
                title = "이용약관"
            }

            if (url.isNotEmpty() && title.isNotEmpty()) {

                binding.toolbarWebView.tvToolbarTitle.text = title
                setWebView(url)
                setClickEvent()
            } else {
                Toast.makeText(this, "잘못된 접근입니다!", Toast.LENGTH_SHORT).show()
                super.onBackPressed()
            }
        }

    }

    private fun setWebView(url: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
    }

    private fun setClickEvent() {
        binding.toolbarWebView.ibBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        Log.e(tag, "OnBackPressed")
        super.onBackPressed()
    }
}