plugins {
    alias(libs.plugins.datalift.android.library)
    alias(libs.plugins.datalift.hilt)
}

android {
    namespace = "com.datalift.testing"
}

dependencies {
    api(libs.bundles.androidx.compose.ui.test)
    api(projects.core.model)


//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
}