plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "com.sapiest.vaultspace.android.library"
            implementationClass = "com.sapiest.vaultspace.plugins.AndroidLibraryPlugin"
            displayName = "Android Library Plugin"
        }
        register("androidHilt") {
            id = "com.sapiest.vaultspace.android.hilt"
            implementationClass = "com.sapiest.vaultspace.plugins.AndroidHiltPlugin"
            displayName = "Hilt Plugin"
        }
        register("androidFeature") {
            id = "com.sapiest.vaultspace.android.feature"
            implementationClass = "com.sapiest.vaultspace.plugins.AndroidFeaturePlugin"
            displayName = "Android Feature Plugin"
        }
        register("kotlinLibrary") {
            id = "com.sapiest.vaultspace.kotlin.library"
            implementationClass = "com.sapiest.vaultspace.plugins.KotlinLibraryPlugin"
            displayName = "Kotlin Library Plugin"
        }
        register("androidRoom") {
            id = "com.sapiest.vaultspace.android.room"
            implementationClass = "com.sapiest.vaultspace.plugins.AndroidRoomPlugin"
            displayName = "Room Plugin"
        }
    }
}