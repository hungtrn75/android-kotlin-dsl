package com.skymapglobal.cctest.workspace.newsfeed.data.local

interface NewsfeedLocalDataSource {
    abstract fun storeDarkModeSetting(mode: Boolean)
    abstract fun retrieveDarkModeSetting(): Boolean
}