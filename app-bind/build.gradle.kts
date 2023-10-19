@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
}

android {
    namespace = "com.sapiest.vaultspace.app.bind"
}

dependencies {
    implementation(project(":features:auth:domain:usecase-impl"))
    implementation(project(":features:auth:domain:data-api"))
    implementation(project(":features:auth:data-impl"))

    implementation(libs.androidx.core.ktx)

}