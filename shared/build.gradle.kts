import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-31"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-3")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
        }
    }
}

android {
    namespace = "kmp.project.demo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
