apply {
    from("$rootDir/library-build.gradle")
}


plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-kapt")
}

dependencies {
    "implementation"(project(Modules.matchesDomain))
    "implementation"(project(Modules.constants))
    "implementation"(project(Modules.core))

    "implementation"(Room.roomCommon)
    "kapt"(Room.roomCompiler)
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
}