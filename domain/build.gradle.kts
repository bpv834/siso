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
    implementation(libs.kotlinx.coroutines.core)

    // Hilt 의존성 추가 (KSP 사용)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    // Gson 컨버터
    implementation(libs.converter.gson.v300)

    // Kotlin Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    // (옵션) Truth 같은 assertion 라이브러리
    testImplementation("com.google.truth:truth:1.4.2")
}
