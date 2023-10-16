package com.sapiest.vaultspace.plugins.extensions

import org.gradle.api.Project
import java.util.Properties

open class LocalPropertyExtension(project: Project) {
    private val properties: Properties = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }

    fun getProperty(propName: String): String {
        return properties[propName] as String? ?: ""
    }
}