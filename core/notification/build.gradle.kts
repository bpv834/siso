plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
 /*   alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)*/
}

android {
    namespace = "com.example.notification"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:util"))
    implementation(project(":domain"))
    implementation(project(":core:ui"))


    // Firebase Messaging (푸시 알림)
    implementation("com.google.firebase:firebase-bom:33.0.0")
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DI
    implementation(libs.hilt.android)
    // ksp(libs.hilt.compiler)
}