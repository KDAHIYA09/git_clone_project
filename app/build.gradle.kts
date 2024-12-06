plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") // Add this line to enable KAPT
}

android {
    namespace = "com.example.gitclone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gitclone"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    val lifecycle_version = "2.6.1"
    val activity_version = "1.7.2"
    val roomVersion = "2.5.1"
    val coroutinesVersion = "1.7.3"
    val materialVersion = "1.9.0"

    kapt ("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    implementation ("androidx.activity:activity-ktx:$activity_version")

    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Material Components
    implementation("com.google.android.material:material:$materialVersion")

    // Room Database
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //circular image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Glide image loading library
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

//    // Room Database
//    implementation("androidx.room:room-runtime:2.5.1")
//    annotationProcessor("androidx.room:room-compiler:2.5.1")  // For Java
//    kapt("androidx.room:room-compiler:2.5.1")  // For Kotlin (use kapt instead of annotationProcessor)
//
//    // LiveData and ViewModel dependencies for MVVM
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")



}