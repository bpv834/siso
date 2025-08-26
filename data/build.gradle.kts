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
    implementation(project(":core:network"))
    implementation(project(":core:util"))
    implementation(project(":core:datastore"))


    // Retrofit (최신 안정 버전)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson 컨버터
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Gson 라이브러리
    implementation("com.google.code.gson:gson:2.10.1")


    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)


    // test
    testImplementation(libs.junit)
    // Kotlin Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    // (옵션) Truth 같은 assertion 라이브러리
    testImplementation("com.google.truth:truth:1.4.2")
    implementation(kotlin("test"))
}

