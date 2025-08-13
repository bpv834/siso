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
}
