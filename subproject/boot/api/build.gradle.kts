@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.serialization)
    `java-test-fixtures`
}

dependencies {
    implementation(projects.subproject.`interface`.platform)
    implementation(libs.grpc.services)
    implementation(libs.kotlinx.serialization.json)
    testFixturesImplementation(projects.subproject.application)
    testFixturesImplementation(testFixtures(projects.subproject.domain))
}
