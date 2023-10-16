package com.sapiest.vaultspace.plugins

import com.sapiest.vaultspace.plugins.ProjectConstants.javaCompileTargetVersion
import com.sapiest.vaultspace.plugins.extensions.java
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        configureKotlin()
    }

    private fun Project.configureKotlin() {
        java {
            sourceCompatibility = JavaVersion.toVersion(javaCompileTargetVersion)
            targetCompatibility = JavaVersion.toVersion(javaCompileTargetVersion)
        }
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply("org.jetbrains.kotlin.jvm")
    }
}