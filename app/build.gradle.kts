import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

val property = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {

    namespace = "com.likelion.siso"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.likelion.siso"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NATIVE_APP_KEY", "\"${property.getProperty("kakao_native_app_key")}\"")
        resValue("string", "kakao_oauth_host", "\"${property.getProperty("kakao_oauth_host")}\"")
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}


dependencies {
    implementation(project(":core:ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":platform:kakao-auth"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit (최신 안정 버전)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson 컨버터
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Gson 라이브러리
    implementation("com.google.code.gson:gson:2.10.1")

    // 카카오
    implementation(libs.v2.user) // 카카오 로그인 API 모듈
}
