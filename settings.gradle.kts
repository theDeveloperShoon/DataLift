pluginManagement {
    includeBuild("build-logic")
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
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "Datalift"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
//include(":core")
include(":core:data")
include(":core:designsystem")
include(":core:screenshot-testing")
include(":core:model")
include(":core:database")
include(":features")
include(":features:feed")
include(":core:ui")
