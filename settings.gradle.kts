pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.aliyun.com/repository/jcenter") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://developer.huawei.com/repo/") }
        maven { url = uri("https://repo.huaweicloud.com/repository/maven") }
        google()
        maven { url = uri("https://jitpack.io") }
        // maven { url = uri("http://maven.aliyun.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "demo"
include(":app")
include(":commonview")
