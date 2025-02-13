plugins {
    id("com.android.library")
    id("maven-publish")
//    alias(libs.plugins.android.library)

//    id("com.github.dcendents.android-maven")
}

android {
    namespace = "cn.lunodio.commonview"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
//        version = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.flexbox)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "cn.lunodio"
            artifactId = "android-common-view"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}