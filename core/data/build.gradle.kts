plugins {
    alias(libs.plugins.datalift.android.library)
    alias(libs.plugins.datalift.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.datalift.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)

    implementation(projects.core.model)

    implementation(libs.firebase.auth.ktx)
}