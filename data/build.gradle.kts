plugins {
    alias(libs.plugins.kotlin.jvm)
    alias (libs.plugins.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)

    // Gson 임포트
    implementation(libs.gson)

    // Kotlin Coroutines Test 테스트 위함
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    // (옵션) Truth 같은 assertion 라이브러리 테스트 위함
    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation(kotlin("test"))
}

