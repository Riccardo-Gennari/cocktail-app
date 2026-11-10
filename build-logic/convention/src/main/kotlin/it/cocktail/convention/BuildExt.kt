package it.cocktail.convention

import git.semver.plugin.gradle.GitSemverPluginExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.HasConfigurableKotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

val GitSemverPluginExtension.versionCode: Int
    get() = (semVersion.major * 10000) + (semVersion.minor * 100) + (semVersion.patch * 10) + semVersion.commitCount

fun KotlinBaseExtension.setupKotlin(kotlinVersionString: String) {
    jvmToolchain(BuildParams.javaVersion.majorVersion.toInt())
    if (this is HasConfigurableKotlinCompilerOptions<*>) {
        val kotlinVersion = extractKotlinVersionFromString(kotlinVersionString)
        compilerOptions {
            apiVersion.set(kotlinVersion)
            languageVersion.set(kotlinVersion)
            progressiveMode.set(true)
        }
    }
}

fun Provider<String>.getAsInt() = get().toInt()

fun Project.setupTesting() {
    tasks.withType<Test> {
        useJUnitPlatform()
        filter.isFailOnNoMatchingTests = false
        failOnNoDiscoveredTests.set(false)
        testLogging {
            events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
            exceptionFormat = TestExceptionFormat.SHORT
            showStandardStreams = false
        }
    }
}

internal fun extractKotlinVersionFromString(kotlinVersionString: String): KotlinVersion {
    val kotlinMajorVersion = kotlinVersionString.split('.').take(2).joinToString(".")
    return KotlinVersion.fromVersion(kotlinMajorVersion)
}
