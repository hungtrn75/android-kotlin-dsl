package com.skymapglobal.cctest.workspace.newsfeed.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.skymapglobal.cctest.databinding.ActivityNewsfeedBinding
import com.skymapglobal.cctest.workspace.details.presentation.DetailsActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsfeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsfeedBinding
    private val viewModel: NewsfeedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsfeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews()
        settingListeners()
    }

    private fun settingViews() {
        setSupportActionBar(binding.appBar)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            binding.switchBtn.isChecked = viewModel.getDarkMode()
            setDarkMode()
        }
    }

    private fun settingListeners() {
        val context = this
        binding.apply {
            switchBtn.setOnClickListener {
                lifecycleScope.launch {
                    val previous = viewModel.getDarkMode()
                    viewModel.setDarkMode(!previous)
                    setDarkMode()
                }
            }

            testBtn.setOnClickListener {
//                viewModel.getNews()
                lifecycleScope.launch {
                    val intent = Intent(context, DetailsActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    private fun setDarkMode() = lifecycleScope.launch {
        AppCompatDelegate.setDefaultNightMode(if (viewModel.getDarkMode()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}