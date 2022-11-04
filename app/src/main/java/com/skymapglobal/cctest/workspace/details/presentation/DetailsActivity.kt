package com.skymapglobal.cctest.workspace.details.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.webkit.WebSettingsCompat.*
import androidx.webkit.WebViewFeature
import com.skymapglobal.cctest.databinding.ActivityDetailsBinding
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val article = "article"
    }

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews(savedInstanceState)
        settingListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.webView.restoreState(savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settingViews(savedInstanceState: Bundle?) {
        val articleExtra = intent.getParcelableExtra<Article>(article)
        if (articleExtra != null) {
            binding.appBar.title = articleExtra.title
            if (savedInstanceState == null) {
                binding.webView.apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.setSupportZoom(false)
                    loadUrl(articleExtra.url!!)
                }
            }
            lifecycleScope.launch {
                binding.switchBtn.apply {
                    isChecked = viewModel.getDarkMode()
                    setDarkMode()
                }
            }
        }

        setSupportActionBar(binding.appBar)
    }

    private fun settingListeners() {
        binding.apply {
            switchBtn.setOnClickListener {
                binding.webView.settings.textZoom = 2
//                lifecycleScope.launch {
//                    viewModel.setDarkMode(!viewModel.getDarkMode())
//                    setDarkMode()
//                }
            }
        }
    }

    private fun setDarkMode() =
        lifecycleScope.launch {
            val mode = viewModel.getDarkMode()
            if (Build.VERSION.SDK_INT >= 33) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
                    setAlgorithmicDarkeningAllowed(binding.webView.settings, mode)
                }
            } else {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    Timber.e("setForceDark: $mode")
                    @Suppress("DEPRECATION")
                    setForceDark(
                        binding.webView.settings,
                        if (mode) FORCE_DARK_ON else FORCE_DARK_OFF
                    )
                }
            }
            AppCompatDelegate.setDefaultNightMode(if (mode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }


    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else
            super.onBackPressed()
    }
}