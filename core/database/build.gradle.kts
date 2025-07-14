plugins {
    alias(libs.plugins.datalift.android.library)
    alias(libs.plugins.datalift.android.room)
    alias(libs.plugins.datalift.hilt)
}

android {
    namespace = "com.datalift.database"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)

    api(libs.androidx.credentials)
    api(libs.google.id)

    implementation(libs.kotlinx.datetime)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
}