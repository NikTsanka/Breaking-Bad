package com.ntsan.breakingbad.app

import android.app.Application
import android.content.Context
import com.ntsan.breakingbad.data.storage.DataStore

class BreakingBadApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.initialize( getSharedPreferences("_sp_", MODE_PRIVATE))
    }
}