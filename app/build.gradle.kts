import it.cocktail.convention.BuildParams
import it.cocktail.convention.getAsInt
import it.cocktail.convention.setupKotlin
import it.cocktail.convention.setupTesting
import it.cocktail.convention.versionCode

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.semanticVersioning)
}

kotlin {
    setupKotlin(
        kotlinVersionString = libs.versions.kotlin.get(),
        javaVersion = libs.versions.jvmTarget.getAsInt(),
    )
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.ktor)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    debugImplementation(libs.compose.uiTooling)

    ksp(libs.hilt.compiler)

    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.ktor.client.mock)
}

android {
    namespace = BuildParams.PACKAGE_NAME
    compileSdk = libs.versions.android.compileSdk
        .getAsInt()

    defaultConfig {
        applicationId = BuildParams.PACKAGE_NAME
        buildConfigField("String", "APP_NAME", "\"${BuildParams.APP_NAME}\"")
        buildConfigField("String", "COCKTAIL_API_KEY", "\"${BuildParams.COCKTAIL_API_KEY}\"")
        manifestPlaceholders["appName"] = BuildParams.APP_NAME
        minSdk = libs.versions.android.minSdk
            .getAsInt()
        targetSdk = libs.versions.android.targetSdk
            .getAsInt()
        versionCode = semver.versionCode
        versionName = semver.version
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            optimization.enable = true
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

setupTesting()
