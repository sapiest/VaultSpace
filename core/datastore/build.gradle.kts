@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id("kotlinx-serialization")
}

android {
    namespace = "com.sapiest.vaultspace.core.datastore"
}

dependencies {
    implementation(project(":core:coroutines"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.security)
    implementation(libs.androidx.datastore)
    api(libs.androidx.datastore.preferences)


    implementation(libs.androidx.core.ktx)
}