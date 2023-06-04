import org.flywaydb.gradle.task.AbstractFlywayTask
import org.flywaydb.gradle.task.FlywayCleanTask
import org.flywaydb.gradle.task.FlywayMigrateTask
import org.jooq.meta.jaxb.EmbeddableDefinitionType
import org.jooq.meta.jaxb.EmbeddableField
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Matchers
import org.jooq.meta.jaxb.MatchersFieldType
import org.jooq.meta.jaxb.MatchersTableType
import org.jooq.meta.jaxb.Property


@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-test-fixtures`
    alias(libs.plugins.jooq)
    alias(libs.plugins.flyway.gradle)
}

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jooq") {
            useVersion("3.18.0")
        }
    }
}

val shardDb = "shard"
val platformDb = "platform"

sourceSets {
    main {
        kotlin {
            srcDirs(
                "src/generated/$platformDb",
                "src/generated/$shardDb"
            )
        }
    }

    testFixtures {
        resources {
            srcDirs("$rootDir/database/")
        }
    }
}

dependencies {
    api(projects.subproject.domain)
    api(libs.bundles.kotlinx)
    api(libs.bundles.hoplite)
    api(libs.bundles.jooq)
    api(libs.bundles.r2dbc)
    implementation(libs.jooq)

    // must have api accessor for using test containers from other test projects
    testFixturesApi(libs.kotest.testcontainer)
    testFixturesApi(libs.testcontainer.r2dbc)
    testFixturesApi(libs.testcontainer.postgresql)
    testFixturesImplementation(libs.kotlinx.coroutines.core)
    testFixturesImplementation(libs.flyway.core)
    testFixturesImplementation(libs.flyway.postgresql)
    testFixturesImplementation(projects.subproject.infrastructure)
    testFixturesImplementation(testFixtures(projects.subproject.domain))

    testImplementation(testFixtures(projects.subproject.infrastructure))

    jooqGenerator("org.postgresql:postgresql:42.5.1")
}


jooq {
    version.set("3.18.0")

    configurations {

        fun Generate.setDefaults() {
            isUdts = true
            isDeprecated = false
            isRecords = true
            isFluentSetters = true

            isPojosAsKotlinDataClasses = false
            isImmutablePojos = false
            isPojos = false

            // cause https://github.com/jOOQ/jOOQ/issues/14785
            isRecordsImplementingRecordN = false
            isKotlinNotNullRecordAttributes = true
            isKotlinNotNullPojoAttributes = true
            isKotlinNotNullInterfaceAttributes = true
        }

        create(shardDb) {
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url =
                        "jdbc:postgresql://localhost:5432/postgres"
                    user = "postgres"
                    password = "platform!123"
                    properties = listOf(
                        Property().apply {
                            key = "PAGE_SIZE"
                            value = "2048"
                        }
                    )
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.apply {}
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = shardDb
                        includes = ".*"
                        excludes = "migration_history"
                    }
                    generate.setDefaults()
                    target.apply {
                        packageName = "io.portone.platform.persistence.model.$shardDb"
                        directory = "src/generated/$shardDb"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }

        create(platformDb) { // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url =
                        "jdbc:postgresql://localhost:5432/postgres"
                    user = "postgres"
                    password = "platform!123"
                    properties = listOf(
                        Property().apply {
                            key = "PAGE_SIZE"
                            value = "2048"
                        }
                    )
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = platformDb
                        includes = ".*"
                        excludes = "migration_history"
                    }
                    generate.setDefaults()
                    target.apply {
                        packageName = "io.portone.platform.persistence.model.$platformDb"
                        directory = "src/generated/$platformDb"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }

        }
    }
}

tasks {
    val platformFlyway: AbstractFlywayTask.() -> Unit = {
        url = "jdbc:postgresql://localhost:5432/postgres"
        defaultSchema = "platform"
        table = "migration_history"
        user = "postgres"
        password = "platform!123"
        connectRetries = 60
        locations = listOf("filesystem:../../database/platform/migration/")
            .toTypedArray()
        cleanDisabled = false
    }
    register<FlywayCleanTask>("platformClean", platformFlyway)
    register<FlywayMigrateTask>("platformMigrate", platformFlyway)

    val shardFlyway: AbstractFlywayTask.() -> Unit = {
        url = "jdbc:postgresql://localhost:5432/postgres"
        defaultSchema = "shard"
        table = "migration_history"
        user = "postgres"
        password = "platform!123"
        connectRetries = 60
        locations = listOf("filesystem:../../database/shard/migration/").toTypedArray()
        cleanDisabled = false
    }

    register<FlywayCleanTask>("shardClean", shardFlyway)
    register<FlywayMigrateTask>("shardMigrate", shardFlyway)

    named("generatePlatformJooq").configure {
        dependsOn("platformClean", "platformMigrate")
    }

    named("generateShardJooq").configure {
        dependsOn("shardClean", "shardMigrate")
    }
}
