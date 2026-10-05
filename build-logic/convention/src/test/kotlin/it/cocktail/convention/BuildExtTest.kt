package it.cocktail.convention

import git.semver.plugin.gradle.GitSemverPluginExtension
import git.semver.plugin.semver.SemInfoVersion
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class BuildExtTest :
    ShouldSpec({
        context("extractKotlinVersionFromString") {
            should("parse standard version strings correctly") {
                extractKotlinVersionFromString("2.4.20") shouldBe KotlinVersion.KOTLIN_2_4
                extractKotlinVersionFromString("1.9.20") shouldBe KotlinVersion.KOTLIN_1_9
                extractKotlinVersionFromString("2.0.0") shouldBe KotlinVersion.KOTLIN_2_0
                extractKotlinVersionFromString("2.1.0") shouldBe KotlinVersion.KOTLIN_2_1
            }

            should("parse version strings with suffixes") {
                extractKotlinVersionFromString("2.0.0-RC1") shouldBe KotlinVersion.KOTLIN_2_0
                extractKotlinVersionFromString("2.1.10-Beta1") shouldBe KotlinVersion.KOTLIN_2_1
            }
        }

        context("versionCode") {
            should("calculate correctly from semVersion major, minor, patch, and commitCount") {
                val extension = mockk<GitSemverPluginExtension>()
                val semVersion = mockk<SemInfoVersion>()

                every { extension.semVersion } returns semVersion
                every { semVersion.major } returns 1
                every { semVersion.minor } returns 2
                every { semVersion.patch } returns 3
                every { semVersion.commitCount } returns 4

                extension.versionCode shouldBe 10234
            }

            should("handle zeros correctly") {
                val extension = mockk<GitSemverPluginExtension>()
                val semVersion = mockk<SemInfoVersion>()

                every { extension.semVersion } returns semVersion
                every { semVersion.major } returns 0
                every { semVersion.minor } returns 0
                every { semVersion.patch } returns 0
                every { semVersion.commitCount } returns 0

                extension.versionCode shouldBe 0
            }

            should("calculate correctly for version 2.15.3 with 12 commits") {
                val extension = mockk<GitSemverPluginExtension>()
                val semVersion = mockk<SemInfoVersion>()

                every { extension.semVersion } returns semVersion
                every { semVersion.major } returns 2
                every { semVersion.minor } returns 15
                every { semVersion.patch } returns 3
                every { semVersion.commitCount } returns 12

                extension.versionCode shouldBe 21542
            }
        }
    })
