plugins {
    alias(libs.plugins.datalift.jvm.library)
    alias(libs.plugins.datalift.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}