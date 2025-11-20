plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.notex"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.notex"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("io.coil-kt:coil-compose:2.7.0")
    // Compose LiveData support
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
// ViewModel integration with Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
// Accompanist System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
     // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp Client
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

    // Logging Interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.11.0")
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // iText 7 Core
    implementation("com.itextpdf:itext7-core:8.0.2")  // latest stable version

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}