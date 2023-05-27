
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-kapt")
}
android {
    compileSdk = Android.compileSdk
    @Suppress("UnstableApiUsage")
    buildToolsVersion = Android.buildTools
    namespace = "com.mhmd.components"

    defaultConfig {
        minSdk = Android.minSdk
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.kotlinCompilerComposeVersion

    }
    packaging {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.constants))
    implementation(Coil.coil)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.util)
    implementation(Compose.navigation)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
}



