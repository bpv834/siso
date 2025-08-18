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
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
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
include(":core:network")
include(":core:storage")
include(":platform:kakao-auth")
include(":core:datastore")
include(":feature:mypage")
