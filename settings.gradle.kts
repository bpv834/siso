pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Siso"
include(":app")
include(":feature:login")
include(":feature:home")
include(":local")
include(":remote")
include(":domain")
include(":data")
include(":core:navigation")
include(":core:util")
include(":core:ui")
include(":feature:main")
