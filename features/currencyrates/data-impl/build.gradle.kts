@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id(libs.plugins.vaultspace.room.get().pluginId)
    id("kotlinx-serialization")
}

android {
    namespace = "com.sapiest.vaultspace.feature.currencyrates.data.impl"
}

dependencies {
    implementation(project(":features:currencyrates:domain:data-api"))

    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:coroutines"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}