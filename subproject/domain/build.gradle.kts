@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-test-fixtures`
    antlr
    java
}

dependencies {
    api(libs.arrow)
    implementation(kotlin("reflect"))
    api(libs.antlr.runtime)
    antlr(libs.antlr.core)

    testFixturesImplementation(projects.subproject.domain)
    testFixturesApi(libs.mockk)
    testFixturesApi(libs.kotest.arrow)
    testFixturesApi(libs.kotest.runner.junit5)
}

val antlrOutputDirectory = "build/generated-src/antlr"

tasks {
    generateGrammarSource {
        arguments = arguments + listOf("-visitor", "-long-messages", "-package", "io.portone.platform.formula.parser")
        maxHeapSize = "64m"
        this.source =
            project.objects.sourceDirectorySet("antlr", "antlr").srcDir("src/main/antlr").apply {
                include("*.g4") // outputDirectory is required, put it into the build directory
            }
        outputDirectory = File("$antlrOutputDirectory/io/portone/platform/formula/parser")
    }

    compileKotlin {
        dependsOn("generateGrammarSource")
        compilerOptions { freeCompilerArgs.add("-nowarn") }
    }
}

sourceSets { main { java { srcDir(antlrOutputDirectory) } } }
