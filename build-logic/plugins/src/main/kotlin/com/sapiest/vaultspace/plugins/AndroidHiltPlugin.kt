package com.sapiest.vaultspace.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        applyDependencies()
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply("dagger.hilt.android.plugin")
        // KAPT must go last to avoid build warnings.
        // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
        apply("org.jetbrains.kotlin.kapt")
    }

    private fun Project.applyDependencies() = dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "kapt"(libs.findLibrary("hilt.compiler").get())
        "kaptAndroidTest"(libs.findLibrary("hilt.compiler").get())
    }
}