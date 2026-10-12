import it.cocktail.convention.setupQualityChecks
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("it.cocktail.convention")
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room3) apply false

    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.semanticVersioning) apply true
}

dependencies {
    "detektPlugins"(libs.detekt.compose.rules)
    "ktlintRuleset"(libs.ktlint.compose.rules)
}

configure<KtlintExtension> {
    version.set(libs.versions.ktlint.core)
}

allprojects {
    setupQualityChecks(
        detektPlugin = rootProject.libs.plugins.detekt,
        ktlintPlugin = rootProject.libs.plugins.ktlint,
        detektRules = rootProject.libs.detekt.compose.rules,
        ktlintRules = rootProject.libs.ktlint.compose.rules,
        ktlintVersion = rootProject.libs.versions.ktlint.core,
    )
}
