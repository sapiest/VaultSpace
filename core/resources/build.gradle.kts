@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
}

android {
    namespace = "com.sapiest.vaultspace.core.resources"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}