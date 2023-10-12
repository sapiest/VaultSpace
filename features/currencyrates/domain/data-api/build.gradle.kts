@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.kotlin.library.get().pluginId)
}

dependencies {
    api(project(":features:currencyrates:common"))

    implementation(libs.jakewharton.android.threetenabp)
}