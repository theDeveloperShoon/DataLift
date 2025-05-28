plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.feed"
}

dependencies {
    implementation(projects.core.data)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}