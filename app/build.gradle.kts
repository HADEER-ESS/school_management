plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.hadeer.schoolapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hadeer.schoolapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://dimgrey-llama-992126.hostingersite.com/api/v1/\"")
            android.buildFeatures.buildConfig = true
        }
        release {
            buildConfigField("String", "API_URL", "\"https://dimgrey-llama-992126.hostingersite.com/api/v1/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //  Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //    OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp3.logging.interceptor)

    //    Retrofit
    implementation(libs.retrofit)
    //    Gson Convertor
    implementation(libs.converter.gson)

//    Navigation component
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

//    Link
    implementation(project(":data"))
}