package com.skymapglobal.cctest.workspace.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.skymapglobal.cctest.databinding.ActivityMainBinding
import com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter.NewsPagerAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        val categories = listOf(
            "general",
            "business",
            "entertainment",
            "health",
            "science",
            "sports",
            "technology"
        )
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews()
        settingListeners()
    }

    private fun settingViews() {
        setSupportActionBar(binding.appBar)
        val pagerAdapter = NewsPagerAdapter(this)
        binding.pager.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = categories[position].replaceFirstChar { it.uppercase() }
        }.attach()

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            binding.switchBtn.isChecked = viewModel.getDarkMode()
            setDarkMode()
        }
    }

    private fun settingListeners() {
        binding.apply {
            switchBtn.setOnClickListener {
                lifecycleScope.launch {
                    val previous = viewModel.getDarkMode()
                    viewModel.setDarkMode(!previous)
                    setDarkMode()
                }
            }
        }
    }

    private fun setDarkMode() = lifecycleScope.launch {
        AppCompatDelegate.setDefaultNightMode(if (viewModel.getDarkMode()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }
}