package com.sapiest.vaultspace.plugins

import com.sapiest.vaultspace.plugins.ProjectConstants.javaCompileTargetVersion
import com.sapiest.vaultspace.plugins.ProjectConstants.javaVersion
import com.sapiest.vaultspace.plugins.ProjectConstants.compileSdkVersion
import com.sapiest.vaultspace.plugins.ProjectConstants.minSdkVersion
import com.sapiest.vaultspace.plugins.extensions.androidLibrary
import com.sapiest.vaultspace.plugins.extensions.kotlin
import com.sapiest.vaultspace.plugins.extensions.kotlinOptions
import com.sapiest.vaultspace.plugins.extensions.localProperties
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.provideDelegate
import java.util.Properties

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        configureAndroid()
        applyDependencies()
    }

    private fun Project.configureAndroid() {
        kotlin {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(javaVersion))
            }
        }

        androidLibrary {
            buildFeatures {
                buildConfig = true
            }

            compileSdk = compileSdkVersion

            defaultConfig {
                minSdk = minSdkVersion

                compileOptions {
                    sourceCompatibility = JavaVersion.toVersion(javaCompileTargetVersion)
                    targetCompatibility = JavaVersion.toVersion(javaCompileTargetVersion)
                }

                buildConfigField(
                    "String",
                    CURRENCY_API_KEY,
                    localProperties().getProperty(CURRENCY_API_KEY)
                )
                buildConfigField(
                    "String",
                    OAUTH_CLIENT_ID,
                    localProperties().getProperty(OAUTH_CLIENT_ID)
                )
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
            }

            kotlinOptions {
                jvmTarget = javaCompileTargetVersion

                // Treat all Kotlin warnings as errors (disabled by default)
                // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
                val warningsAsErrors: String? by project
                allWarningsAsErrors = warningsAsErrors.toBoolean()
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                )
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("androidTestImplementation", kotlin("test"))
            add("testImplementation", kotlin("test"))
        }
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
        apply("com.sapiest.vaultspace.android.hilt")
    }

    private companion object {
        const val CURRENCY_API_KEY = "CURRENCY_API_KEY"
        const val OAUTH_CLIENT_ID = "OAUTH_CLIENT_ID"
    }
}