plugins {
    id("com.android.library")
    id("maven-publish")
//    id 'com.vanniktech.maven.publish'
}

android {
    namespace = "cn.lunodio.commonview"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
//        targetSdk = 34
        testOptions {
            targetSdk = 34
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

//    publishing{
//        publications {
//            create<MavenPublication>("release") {
//                from(components["release"])
//            }
//        }
//        repositories {
//            mavenLocal()
//        }
//    }
}
publishing {
    publications {
        create<MavenPublication>("CommonView") {
            from(components["java"]) // 根据你的项目需要选择组件，像 'java' 或 'kotlin'

            groupId = "cn.lunodio"
            artifactId = "android-common-view"
            version = "1.0.0"
        }

    }
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation(libs.flexbox)
    implementation(libs.flexbox)
}
