package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.skymapglobal.cctest.databinding.ActivityNewsfeedBinding
import timber.log.Timber

class NewsfeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsfeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityNewsfeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews()
        settingListeners()
    }
    private fun settingViews() {
        setSupportActionBar(binding.appBar)
    }

    private fun settingListeners() {
        binding.apply {
            switchBtn.setOnCheckedChangeListener { _, value ->
                AppCompatDelegate.setDefaultNightMode(if (!value) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}