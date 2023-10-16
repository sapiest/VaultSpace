package com.sapiest.vaultspace.plugins

import com.sapiest.vaultspace.plugins.extensions.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeaturePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        configureAndroid()
        applyDependencies()
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply("com.sapiest.vaultspace.android.library")
    }

    private fun Project.configureAndroid() {
        androidLibrary {
            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("implementation", project(":core:network"))

            add("testImplementation", kotlin("test"))
            add("androidTestImplementation", kotlin("test"))
        }
    }
}