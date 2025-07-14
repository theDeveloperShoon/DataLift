plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.login"
}

dependencies {
    api(projects.core.data)

    implementation(libs.androidx.credentials)
    implementation(libs.google.id)
}