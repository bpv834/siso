plugins {
    id("com.android.library")
    kotlin("android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.likelion.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":local"))
    implementation(project(":remote"))

    // Retrofit (최신 안정 버전)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson 컨버터
    implementation(libs.converter.gson.v300)


    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}

