@file:Suppress("UnstableApiUsage")

import com.android.build.api.variant.HostTestBuilder
import it.cocktail.convention.BuildParams
import it.cocktail.convention.setupKotlin
import it.cocktail.convention.setupTesting
import it.cocktail.convention.versionCode

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kover) apply true
    alias(libs.plugins.ksp)
    alias(libs.plugins.room3)
    alias(libs.plugins.semanticVersioning)
}

kotlin {
    setupKotlin(kotlinVersionString = libs.versions.kotlin.get())
}

semver {
    defaultPreRelease = ""
    noDirtyCheck = true
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.ktor)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.room3.runtime)

    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.composeTestManifest)

    ksp(libs.hilt.compiler)
    ksp(libs.room3.compiler)

    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.bundles.composeTest)
    testImplementation(libs.composeTestManifest)
    testImplementation(libs.ktor.client.mock)
}

android {
    namespace = BuildParams.PACKAGE_NAME
    compileSdk = BuildParams.COMPILE_SDK

    defaultConfig {
        applicationId = BuildParams.PACKAGE_NAME
        buildConfigField("String", "APP_NAME", "\"${BuildParams.APP_NAME}\"")
        buildConfigField("String", "COCKTAIL_API_KEY", "\"${BuildParams.COCKTAIL_API_KEY}\"")
        manifestPlaceholders["appName"] = BuildParams.APP_NAME
        minSdk = BuildParams.MIN_SDK
        targetSdk = BuildParams.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = semver.versionCode
        versionName = semver.version
    }

    compileOptions {
        sourceCompatibility = BuildParams.javaVersion
        targetCompatibility = BuildParams.javaVersion
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all { test ->
                test.jvmArgs(
                    "--add-opens=java.base/java.lang=ALL-UNNAMED",
                    "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
                    "--add-opens=java.base/java.io=ALL-UNNAMED",
                    "--add-opens=java.base/java.util=ALL-UNNAMED",
                )
            }
        }
    }
}

androidComponents {
    beforeVariants { variantBuilder ->
        variantBuilder.hostTests[HostTestBuilder.UNIT_TEST_TYPE]?.enable = true
    }
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

setupTesting()

kover {
    reports {
        filters {
            excludes {
                annotatedBy("androidx.compose.runtime.Composable")
                annotatedBy("androidx.compose.ui.tooling.preview.Preview")
                classes("*ComposableSingletons*", "*LiveLiterals*", "*$*DefaultImpls", "*_*")
                packages("dagger.hilt", "hilt_aggregated_deps", "*.composable")
            }
        }
    }
}
