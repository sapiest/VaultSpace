/**
 * The first section in the build configuration applies the Android Gradle plugin
 * to this build and makes the android block available to specify
 * Android-specific build options.
 */
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    id(libs.plugins.vaultspace.hilt.get().pluginId)
    id(libs.plugins.vaultspace.room.get().pluginId)
}

/**
 * Locate (and possibly download) a JDK used to build your kotlin
 * source code. This also acts as a default for sourceCompatibility,
 * targetCompatibility and jvmTarget. Note that this does not affect which JDK
 * is used to run the Gradle build itself, and does not need to take into
 * account the JDK version required by Gradle plugins (such as the
 * Android Gradle Plugin)
 */
kotlin {
    jvmToolchain(17)
}

/**
 * The android block is where you configure all your Android-specific
 * build options.
 */
android {

    /**
     * The app's namespace. Used primarily to access app resources.
     */
    namespace = "com.sapiest.vaultspace"

    /**
     * compileSdk specifies the Android API level Gradle should use to
     * compile your app. This means your app can use the API features included in
     * this API level and lower.
     */
    compileSdk = 34
    /**
     * The defaultConfig block encapsulates default settings and entries for all
     * build variants and can override some attributes in main/AndroidManifest.xml
     * dynamically from the build system. You can configure product flavors to override
     * these values for different versions of your app.
     */
    defaultConfig {

        // Uniquely identifies the package for publishing.
        applicationId = "com.sapiest.vaultspace"

        // Defines the minimum API level required to run the app.
        minSdk = 23

        // Specifies the API level used to test the app.
        targetSdk = 33

        // Defines the version number of your app.
        versionCode = 1

        // Defines a user-friendly version name for your app.
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    /**
     * The buildTypes block is where you can configure multiple build types.
     * By default, the build system defines two build types: debug and release. The
     * debug build type is not explicitly shown in the default build configuration,
     * but it includes debugging tools and is signed with the debug key. The release
     * build type applies ProGuard settings and is not signed by default.
     */
    buildTypes {

        /**
         * By default, Android Studio configures the release build type to enable code
         * shrinking, using minifyEnabled, and specifies the default ProGuard rules file.
         */
        getByName("release") {

            // Enables code shrinking for the release build type.
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isDebuggable = true
        }
    }

    /**
     * The productFlavors block is where you can configure multiple product flavors.
     * This lets you create different versions of your app that can
     * override the defaultConfig block with their own settings. Product flavors
     * are optional, and the build system does not create them by default.
     *
     * This example creates a free and paid product flavor. Each product flavor
     * then specifies its own application ID, so that they can exist on the Google
     * Play Store, or an Android device, simultaneously.
     *
     * If you declare product flavors, you must also declare flavor dimensions
     * and assign each flavor to a flavor dimension.
     * */
    // flavorDimensions += "tier"
    // productFlavors {
    //     create("free") {
    //         dimension = "tier"
    //         applicationIdSuffix = ".free"
    //     }
    //
    //     create("paid") {
    //         dimension = "tier"
    //         applicationIdSuffix = ".paid"
    //     }
    // }
    //

    /**
     * To override source and target compatibility (if different from the
     * toolchain JDK version), add the following. All of these
     * default to the same value as kotlin.jvmToolchain. If you're using the
     * same version for these values and kotlin.jvmToolchain, you can
     * remove these blocks.
     *
     * The sourceCompatibility property determines which Java language features are available during compilation of Java source.
     * It does not affect Kotlin source.
     *
     * Specifying targetCompatibility and jvmTarget determines the Java class-format version used
     * when generating bytecode for compiled Java and Kotlin source, respectively.
     */
//      compileOptions {
//          sourceCompatibility = JavaVersion.VERSION_17
//          targetCompatibility = JavaVersion.VERSION_17
//      }
//      kotlinOptions {
//          jvmTarget = "17"
//      }
}

/**
 * The dependencies block in the module-level build configuration file
 * specifies dependencies required to build only the module itself.
 * To learn more, go to Add build dependencies.
 */
dependencies {
    implementation(project(":core:database"))
    implementation(project(":features:currencyrates:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}