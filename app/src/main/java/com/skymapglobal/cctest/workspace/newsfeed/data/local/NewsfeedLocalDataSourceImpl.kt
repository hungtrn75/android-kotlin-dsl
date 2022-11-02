package com.skymapglobal.cctest.workspace.newsfeed.data.local

import android.content.SharedPreferences
import com.skymapglobal.cctest.core.util.Constants

class NewsfeedLocalDataSourceImpl constructor(private val mSharedPreferences: SharedPreferences) :
    NewsfeedLocalDataSource {
    override fun storeDarkModeSetting(mode: Boolean) {
        mSharedPreferences.edit().putBoolean(Constants.darkModeSettingKey, mode).apply()
    }

    override fun retrieveDarkModeSetting(): Boolean {
        return mSharedPreferences.getBoolean(Constants.darkModeSettingKey, false)
    }

}