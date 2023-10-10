package com.sapiest.vaultspace.plugins

import org.gradle.api.JavaVersion

internal object ProjectConstants {
    const val javaVersion: String = "17"
    val javaCompileTargetVersion: String = JavaVersion.VERSION_17.toString()
    const val compileSdkVersion: Int = 34
    const val minSdkVersion: Int = 21
}