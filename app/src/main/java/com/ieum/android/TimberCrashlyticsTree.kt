package com.ieum.android

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class TimberCrashlyticsTree : Timber.Tree() {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        if (priority != Log.ERROR) return
        crashlytics.log("[$tag] $message")
        t?.let { crashlytics.recordException(it) }
    }
}