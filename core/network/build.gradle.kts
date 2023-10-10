@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id("kotlinx-serialization")
}

android {
    namespace = "com.sapiest.vaultspace.core.network"
}

dependencies {
    implementation(project(":core:coroutines"))

    api(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.jakewharton.android.threetenabp)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.core.ktx)
}