plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.settings"
}

dependencies {
    implementation(projects.core.data)
}