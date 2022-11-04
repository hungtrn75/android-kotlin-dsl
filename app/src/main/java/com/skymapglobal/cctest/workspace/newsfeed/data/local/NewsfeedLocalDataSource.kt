package com.skymapglobal.cctest.workspace.newsfeed.data.local

interface NewsfeedLocalDataSource {
    fun storeDarkModeSetting(mode: Boolean)
    fun retrieveDarkModeSetting(): Boolean
}