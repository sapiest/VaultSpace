pluginManagement {
    /**
     * The pluginManagement.repositories block configures the
     * repositories Gradle uses to search or download the Gradle plugins and
     * their transitive dependencies. Gradle pre-configures support for remote
     * repositories such as JCenter, Maven Central, and Ivy. You can also use
     * local repositories or define your own remote repositories. The code below
     * defines the Gradle Plugin Portal, Google's Maven repository,
     * and the Maven Central Repository as the repositories Gradle should use to look for its
     * dependencies.
     */
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {

    /**
     * The dependencyResolutionManagement.repositories
     * block is where you configure the repositories and dependencies used by
     * all modules in your project, such as libraries that you are using to
     * create your application. However, you should configure module-specific
     * dependencies in each module-level build.gradle file. For new projects,
     * Android Studio includes Google's Maven repository and the Maven Central
     * Repository by default, but it does not configure any dependencies (unless
     * you select a template that requires some).
     */
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Vault Space"
include(":app")
include(":core:network")
include(":core:coroutines")
include(":core:database")
include(":features:currencyrates:common")
include(":features:currencyrates:domain")
include(":features:currencyrates:domain:data-api")
include(":features:currencyrates:domain:usecase-impl")
include(":features:currencyrates:data-impl")
include(":sync")
include(":features:auth")
include(":features:auth:domain")
include(":features:auth:data-impl")
include(":features:auth:domain:data-api")
include(":core:resources")
include(":core:datastore")
include(":features:auth:domain:usecase-impl")
include(":app-bind")
