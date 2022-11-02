package com.skymapglobal.cctest.workspace.newsfeed.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.skymapglobal.cctest.databinding.ActivityNewsfeedBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsfeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsfeedBinding
    private val viewModel: NewsfeedViewModel by viewModel()

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
            testBtn.setOnClickListener {
                viewModel.getNews()
            }
        }
    }
}