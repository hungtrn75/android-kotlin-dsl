package com.skymapglobal.cctest.core.util

import androidx.annotation.Nullable
import timber.log.Timber


class NotLoggingTree : Timber.Tree() {
    override fun log(
        priority: Int, @Nullable tag: String?,
        message: String, @Nullable t: Throwable?
    ) {
        // Do nothing here
    }
}

class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "C:%s:%s",
            super.createStackElementTag(element),
            element.lineNumber
        )
    }
}