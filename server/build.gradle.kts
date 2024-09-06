import org.jetbrains.kotlin.gradle.dsl.JvmTarget

ext.set("localDaogenVersion", "PERMISSIONS")

plugins {
    `maven-publish`
    id("global.genesis.genesis-start-gui")
}

subprojects {
    apply(plugin = "org.gradle.maven-publish")

    dependencies {
        implementation("com.h2database:h2:2.2.224")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    }
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict", "-jvm-default=no-compatibility")
                jvmTarget = JvmTarget.JVM_17
            }
        }
    }
}

tasks {
    assemble {
        for(subproject in subprojects){
            dependsOn(subproject.tasks.named("assemble"))
        }
    }
    build {
        for(subproject in subprojects){
            dependsOn(subproject.tasks.named("build"))
        }
    }
    clean {
        for(subproject in subprojects){
            dependsOn(subproject.tasks.named("clean"))
        }
    }
    test {
        for(subproject in subprojects){
            dependsOn(subproject.tasks.named("test"))
        }
    }
}

allprojects {

    group = "global.genesis"
    version = "1.0.0-SNAPSHOT"


    kotlin {
        jvmToolchain {
            (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    tasks.withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        mavenLocal {
            // VERY IMPORTANT!!! EXCLUDE AGRONA AS IT IS A POM DEPENDENCY AND DOES NOT PLAY NICELY WITH MAVEN LOCAL!
            content {
                excludeGroup("org.agrona")
            }
        }
        maven {
            val repoUrl = if(findProperty("useDevRepo") == "true") {
                "https://genesisglobal.jfrog.io/genesisglobal/dev-repo"
            } else {
                "https://genesisglobal.jfrog.io/genesisglobal/libs-release-client"
            }
            url = uri(repoUrl)
            credentials {
                username = findProperty("genesisArtifactoryUser").toString()
                password = findProperty("genesisArtifactoryPassword").toString()
            }
        }
        if (findProperty("useDevRepo") != "true") {
            mavenCentral()
        }
        google()
    }

    publishing {
        publications.create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    tasks{
        test {
            systemProperty("DbLayer", "SQL")
            systemProperty("DbHost", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
            systemProperty("DbQuotedIdentifiers", "true")
            useJUnitPlatform()
        }
    }
}
