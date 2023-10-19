package com.sapiest.vaultspace.plugins.extensions

import com.android.build.api.dsl.LibraryExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.androidLibrary(configure: Action<LibraryExtension>) {
    extensions.configure("android", configure)
}

internal fun Project.kotlin(configure: Action<KotlinAndroidProjectExtension>) {
    extensions.configure("kotlin", configure)
}

internal fun Project.java(configure: Action<JavaPluginExtension>) {
    extensions.configure("java", configure)
}

internal fun Project.ksp(configure: Action<KspExtension>) {
    extensions.configure("ksp", configure)
}

internal fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
}

internal fun Project.localProperties(): LocalPropertyExtension =
    if (extensions.findByName("localProps") == null) {
        extensions.create(
            "localProps",
            LocalPropertyExtension::class.java,
            this
        )
    } else {
        extensions.get("localProps") as LocalPropertyExtension
    }
