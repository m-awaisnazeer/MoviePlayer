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

rootProject.name = "MoviePlayer"
include(":app")
include(":core:data")
include(":core:ui")
include(":core:utils")
include(":core:domain")
include(":feature:searchmovie")
include(":feature:moviedetail")
include(":feature:movieplayer")
