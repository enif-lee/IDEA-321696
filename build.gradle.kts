import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

// will be removed Gradle 8.1 - see https://github.com/gradle/gradle/issues/22797#issuecomment-1407399292
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.kotlinter)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.protobuf) apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}

val lib = rootProject.libs
subprojects {
    group = "io.portone.platform"
    version = "1.0-SNAPSHOT"

    apply {
        plugin(lib.plugins.kotlin.jvm.get().pluginId)
        plugin(lib.plugins.gradle.kotlinter.get().pluginId)
    }

    dependencies {
        implementation(lib.kotlinx.coroutines.jdk8)
        testImplementation(kotlin("test"))
        testImplementation(testFixtures(rootProject.projects.subproject.domain))
        testImplementation(lib.bundles.kotest)
        testImplementation(lib.mockk)
    }

    kotlin {
        jvmToolchain(17)
    }

    tasks {
        compileKotlin {
            compilerOptions {
                freeCompilerArgs.addAll("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
            }
        }
        compileTestKotlin {
            compilerOptions {
                freeCompilerArgs.addAll("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
            }
        }

        formatKotlinMain {
            exclude { it.file.path.contains("generated") }
        }
        lintKotlinMain {
            exclude { it.file.path.contains("generated") }
        }

        test {
            useJUnitPlatform()
            testLogging {
                showStandardStreams = true
                events.add(TestLogEvent.FAILED)
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}
