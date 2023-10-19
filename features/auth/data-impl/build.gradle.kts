@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id("kotlinx-serialization")
}

android {
    namespace = "com.sapiest.vaultspace.features.auth.data.impl"
}

dependencies {
    implementation(project(":features:auth:domain:data-api"))
    implementation(project(":core:datastore"))
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.service.auth)

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}