plugins {
    id("com.android.application")

}

android {
    namespace = "com.example.pdf_view_micro_project"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.pdf_view_micro_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}

dependencies {
    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.activity:activity-ktx:1.3.0")
    implementation ("androidx.fragment:fragment-ktx:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("com.karumi:dexter:6.2.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}