package com.skymapglobal.cctest.workspace.details.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
import com.skymapglobal.cctest.databinding.ActivityDetailsBinding
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews()
        settingListeners()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settingViews() {
        setSupportActionBar(binding.appBar)
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.setSupportZoom(false)
            loadUrl("https://techcrunch.com/2022/11/02/crypto-consumer-web3-solo-female-gp-magdalena-kala-double-down/")
        }
    }

    private fun settingListeners() {
        binding.apply {
            switchBtn.setOnCheckedChangeListener { _, value ->
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        binding.webView.settings,
                        if (!value) FORCE_DARK_OFF else FORCE_DARK_ON
                    )
                }
                AppCompatDelegate.setDefaultNightMode(if (!value) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else
            super.onBackPressed()
    }
}