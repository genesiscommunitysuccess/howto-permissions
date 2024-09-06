rootProject.name = "genesisproduct-permissions"

pluginManagement {
    pluginManagement {
        val genesisVersion = settings.providers.gradleProperty("genesisVersion").get()
        val startVersion = settings.providers.gradleProperty("startVersion").get()

        plugins {
            id("global.genesis.settings") version genesisVersion
            id("global.genesis.genesis-start-gui") version startVersion
        }
    }

    repositories {
        maven {
            val repoUrl = if(extra.properties["useDevRepo"] == "true") {
                "https://genesisglobal.jfrog.io/genesisglobal/dev-repo"
            } else {
                "https://genesisglobal.jfrog.io/genesisglobal/libs-release-client"
            }
            url = uri(repoUrl)
            credentials {
                username = extra.properties["genesisArtifactoryUser"].toString()
                password = extra.properties["genesisArtifactoryPassword"].toString()
            }
        }
        gradlePluginPortal()
        if (extra.properties["useDevRepo"] != "true") {
            mavenCentral()
        }
        mavenLocal {
            // VERY IMPORTANT!!! EXCLUDE AGRONA AS IT IS A POM DEPENDENCY AND DOES NOT PLAY NICELY WITH MAVEN LOCAL!
            content {
                excludeGroup("org.agrona")
            }
        }
        google()
    }
}

plugins {
    id("global.genesis.settings")
}

genesis {
    dependencies {
        dependency("global.genesis:auth:${extra.properties["authVersion"]}")

    }

    plugins {
        genesisDeploy.enabled = true
    }

}

include("permissions-app")
