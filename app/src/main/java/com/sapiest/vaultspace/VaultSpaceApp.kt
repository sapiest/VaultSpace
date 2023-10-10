package com.sapiest.vaultspace

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaultSpaceApp: Application() {

    override fun onCreate() {
        AndroidThreeTen.init(this)

        super.onCreate()
    }
}