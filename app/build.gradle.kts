plugins {
    alias(libs.plugins.datalift.android.application)
    alias(libs.plugins.datalift.android.application.compose)
    alias(libs.plugins.datalift.android.application.flavors)
    alias(libs.plugins.datalift.android.application.jacoco)
    alias(libs.plugins.datalift.hilt)

    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.datalift"

    defaultConfig {
        applicationId = "com.example.datalift"
        versionCode = 1
        versionName = "1.0.0"  // X.Y.Z, X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.features.feed)
    implementation(projects.features.logging)
    implementation(projects.features.login)
    implementation(projects.features.settings)

    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
//    implementation("androidx.navigation:navigation-compose:2.5.3")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.navigation.fragment)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Vico
    implementation(libs.vico.compose)
    implementation(libs.vico.views)

//    //Compose Charts
//    implementation(libs.philjay.mpandroidchart)


    //Autofill
    implementation(libs.androidx.autofill)

    //Volley
    implementation("com.android.volley:volley:1.2.1")



    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    kspTest(libs.hilt.compiler)


    testImplementation(libs.robolectric)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.roborazzi)

    //Mockk
    testImplementation(libs.mockk)

    //coroutinesTesting
    testImplementation(libs.kotlinx.coroutines.test)
}

