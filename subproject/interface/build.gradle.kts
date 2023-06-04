import com.google.protobuf.gradle.id

@Suppress("DSL_SCOPE_VIOLATION")
plugins { alias(libs.plugins.protobuf) }

val lib = rootProject.libs

subprojects {
    apply {
        plugin(lib.plugins.protobuf.get().pluginId)
    }

    tasks {
        generateProto.configure {
            outputs.upToDateWhen { false }
            outputs.cacheIf { false }
        }
    }

    sourceSets {
        main {
            proto { srcDirs("interface/protobuf") }
            java {
                srcDirs(
                    "$buildDir/generated/source/proto/main/grpc",
                    "$buildDir/generated/source/proto/main/grpckt",
                    "$buildDir/generated/source/proto/main/java",
                    "$buildDir/generated/source/proto/main/kotlin"
                )
            }
        }
    }

    val versions = lib.versions
    protobuf {
        protoc { artifact = "com.google.protobuf:protoc:${versions.protobuf.asProvider().get()}" }
        plugins {
            id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:${versions.grpc.asProvider().get()}" }
            id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:${versions.grpc.kotlin.get()}:jdk8@jar" }
        }
        generateProtoTasks {
            ofSourceSet("main").forEach {
                it.plugins {
                    id("grpc")
                    id("grpckt")
                }
                it.builtins { id("kotlin") }
            }
        }
    }

    dependencies { api(lib.bundles.grpc) }
}
