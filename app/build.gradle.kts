plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin") version "2.5.3"
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services") version "4.3.15"




}


android {
    namespace = "com.example.olymperia"
    compileSdk = 34 // Usa una versión estable

    defaultConfig {
        applicationId = "com.example.olymperia"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
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
    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")


// Firebase Auth (para autenticar al usuario)


// Firebase Realtime Database (para almacenar y consultar datos)
    implementation("com.google.firebase:firebase-database-ktx")
    
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))


    // Core
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Material
    implementation("com.google.android.material:material:1.9.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // Lifecycle & activity
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit + Gson
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.code.gson:gson:2.10.1")
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth:22.1.2")

// Firebase core (analytics opcional pero útil)
    implementation("com.google.firebase:firebase-analytics:21.5.1")


    // Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")



}
