plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.noteapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.noteapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        create("profile") {
            // Kế thừa cấu hình từ debug cho tiện (symbols, debuggable, vv.)
            initWith(getByName("debug"))
            isDebuggable = true

            // Khi một số lib/variant không có biến thể "profile",
            // Gradle sẽ fallback sang "debug" để giải quyết phụ thuộc/manifest
            matchingFallbacks += listOf("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val room_version = "2.8.1"

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    debugImplementation("com.example.flutter_counter_module:flutter_debug:1.0")
//    releaseImplementation("com.example.flutter_counter_module:flutter_release:1.0")
//    add("profileImplementation", "com.example.flutter_counter_module:flutter_profile:1.0")
    debugImplementation("com.example.genhub_sdk:flutter_debug:1.0")
    releaseImplementation("com.example.genhub_sdk:flutter_profile:1.0")
    add("profileImplementation", "com.example.genhub_sdk:flutter_release:1.0")

}