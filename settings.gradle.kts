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
        maven("https://storage.googleapis.com/download.flutter.io")
//        maven(url = "/Users/admin/flutter_project/flutter_counter_module/build/host/outputs/repo")
//        maven(url = "${rootDir}/app/flutter_counter_module/repo")
        maven(url = "${rootDir}/app/genhub_ai_module/repo")
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Note App"
include(":app")
 