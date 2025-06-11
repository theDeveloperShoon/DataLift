plugins {
    alias(libs.plugins.datalift.android.feature)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.logging"
}

dependencies {
    implementation(projects.core.data)

}