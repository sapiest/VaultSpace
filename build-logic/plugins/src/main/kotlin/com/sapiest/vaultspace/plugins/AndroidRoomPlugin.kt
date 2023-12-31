package com.sapiest.vaultspace.plugins

import com.sapiest.vaultspace.plugins.extensions.ksp
import com.sapiest.vaultspace.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.dependencies
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class AndroidRoomPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        applyDependencies()
        configureKsp()
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply("com.google.devtools.ksp")
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("implementation", libs.findLibrary("room.runtime").get())
            add("implementation", libs.findLibrary("room.ktx").get())
            add("ksp", libs.findLibrary("room.compiler").get())
        }
    }

    private fun Project.configureKsp() {
        ksp {
            // The schemas directory contains a schema file for each version of the Room database.
            // This is required to enable Room auto migrations.
            // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
            val file = File(projectDir, "schemas")
            if (file.exists()) {
                arg(RoomSchemaArgProvider(file))
            }
        }
    }

    /**
     * https://issuetracker.google.com/issues/132245929
     * [Export schemas](https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schemas)
     */
    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}