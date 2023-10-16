@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
}

android {
    namespace = "com.sapiest.vaultspace.sync"
}

dependencies {
    implementation(project(":features:currencyrates:domain:data-api"))

    implementation(libs.androidx.work.ktx)
    implementation(libs.androidx.work)
    implementation(libs.hilt.work)
}