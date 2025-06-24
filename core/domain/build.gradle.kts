plugins {
    alias(libs.plugins.datalift.android.library)
    alias(libs.plugins.datalift.android.library.jacoco)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.datalift.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.javax.inject)
}