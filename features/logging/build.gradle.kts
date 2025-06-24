plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.datalift.logging"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}