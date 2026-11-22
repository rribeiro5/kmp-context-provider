rootProject.name = "kmp-context-provider"

pluginManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
    }
}

include(":kmp-context-provider")
include(":kmp-context-provider-no-deps")

val includeSamples = providers.gradleProperty("includeSamples").orNull == "true"
    || System.getProperty("idea.active") == "true"

if (includeSamples) {
    include(":samples:lib")
    include(":samples:app")
}


