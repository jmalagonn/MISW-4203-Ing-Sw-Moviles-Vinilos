plugins {
    id("com.android.application") version "8.6.0"
    id("org.jetbrains.kotlin.android") version "1.9.0"
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
    id("kotlin-kapt")
}


android {
    namespace = "co.edu.uniandes.misw4203.proyectovinilos"
    compileSdk = 34

    defaultConfig {
        applicationId = "co.edu.uniandes.misw4203.proyectovinilos"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding{
        enable = true
    }

    viewBinding {
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.glide)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.room.common)
    ksp(libs.ksp.glide)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.volley)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidTest)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.room)
    ksp(libs.roomCompiler)
    androidTestImplementation(libs.roomTesting)
    implementation(libs.gson)
}