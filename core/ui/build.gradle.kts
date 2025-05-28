plugins {
    alias(libs.plugins.datalift.android.library)
    alias(libs.plugins.datalift.android.library.compose)
}

android {
    namespace = "com.datalift.ui"
}

dependencies {

    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}