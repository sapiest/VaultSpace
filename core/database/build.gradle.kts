@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.vaultspace.android.library.get().pluginId)
    id(libs.plugins.vaultspace.room.get().pluginId)
}

android {
    namespace = "com.sapiest.vaultspace.core.database"
}

dependencies {
    api(libs.jakewharton.android.threetenabp)
}