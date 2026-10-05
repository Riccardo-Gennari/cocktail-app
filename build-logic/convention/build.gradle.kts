plugins {
    `kotlin-dsl`
}

private val pluginPackageName = "it.cocktail"

group = "$pluginPackageName.buildlogic"

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ktlint.gradlePlugin)
    implementation(libs.semanticVersioning.gradlePlugin)

    testImplementation(libs.bundles.unitTest)
}

gradlePlugin {
    plugins {
        register("$pluginPackageName.convention") {
            id = "$pluginPackageName.convention"
            implementationClass = "$pluginPackageName.convention.ConventionPlugin"
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
