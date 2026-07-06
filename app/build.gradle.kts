import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

val properties = Properties()
properties.load(
    project.rootProject.file("local.properties")
        .inputStream()
)

val tmdbToken = properties.getProperty("TMDB_TOKEN")

val baseUrl = properties.getProperty("BASE_URL")

val imageUrl = properties.getProperty("IMAGE_URL")

android {
    namespace = "com.atf.moviedb"

    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.atf.moviedb"

        minSdk = 24
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField(
            "String",
            "TMDB_TOKEN",
            "\"$tmdbToken\""
        )

        buildConfigField(
            "String",
            "BASE_URL",
            "\"$baseUrl\""
        )

        buildConfigField(
            "String",
            "IMAGE_URL",
            "\"$imageUrl\""
        )
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true

        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)

    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.hilt.compiler)

    implementation(libs.ktor.client.core)

    implementation(libs.ktor.client.android)

    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.ktor.serialization.json)

    implementation(libs.ktor.client.logging)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.paging.compose)

    implementation(libs.coil.compose)

    implementation(libs.androidx.datastore)

    implementation(libs.koin.core)

    implementation(libs.koin.compose)

    implementation(libs.koin.compose.viewmodel)

    implementation(libs.koin.android)

    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.compose.material)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // tambahan untuk Room + PagingSource
    implementation(libs.androidx.room.paging)

    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)

    testImplementation(libs.mockk)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.turbine)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.paging.compose)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
}