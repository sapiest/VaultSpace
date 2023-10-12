@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id(libs.plugins.vaultspace.hilt.get().pluginId)
}
android {
    namespace = "com.sapiest.vaultspace.feature.curencyrates.domain.usecase.impl"
}

dependencies {
    implementation(project(":features:currencyrates:domain:data-api"))
}