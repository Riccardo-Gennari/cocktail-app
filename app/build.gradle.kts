import it.cocktail.convention.BuildParams
import it.cocktail.convention.getAsInt
import it.cocktail.convention.setupKotlin
import it.cocktail.convention.versionCode

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
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
    implementation(libs.compose.uiToolingPreview)

    debugImplementation(libs.compose.uiTooling)

    testImplementation(libs.bundles.unitTest)
}

android {
    namespace = BuildParams.PACKAGE_NAME
    compileSdk = libs.versions.android.compileSdk
        .getAsInt()

    defaultConfig {
        applicationId = BuildParams.PACKAGE_NAME
        buildConfigField("String", "APP_NAME", "\"${BuildParams.APP_NAME}\"")
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

tasks.withType<Test> {
    useJUnitPlatform()
    filter.isFailOnNoMatchingTests = false
    failOnNoDiscoveredTests = false
}
