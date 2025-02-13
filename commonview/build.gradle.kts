plugins {
//    alias(libs.plugins.android.application)
    id("com.android.library")
}

android {
    namespace = "cn.lunodio.commonview"
    compileSdk = 34

    defaultConfig {
//        applicationId = "cn.lunodio.commonview"
        minSdk = 23
        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"

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
}

dependencies {
//    implementation 'androidx.appcompat:appcompat:1.2.0'
//    implementation 'com.google.android.material:material:1.3.0'
//    implementation 'org.jetbrains:annotations:15.0'
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
//    implementation 'com.google.android:flexbox:2.0.1'


    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation(libs.flexbox)
    implementation(libs.flexbox)
}
