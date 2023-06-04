subprojects {
    apply {
        plugin("org.gradle.application")
    }

    dependencies {
        implementation(rootProject.projects.subproject.application)
        implementation(rootProject.projects.subproject.infrastructure)
        testImplementation(testFixtures(rootProject.projects.subproject.infrastructure))
    }
}
