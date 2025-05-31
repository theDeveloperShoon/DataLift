plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.logging"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}