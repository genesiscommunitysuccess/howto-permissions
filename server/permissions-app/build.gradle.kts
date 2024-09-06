plugins {
    id("global.genesis.allure") version "1.0.0"
}

dependencies {
    compileOnly(genesis("script-dependencies"))
    genesisGeneratedCode(withTestDependency = true)
    testImplementation(genesis("dbtest"))
    testImplementation(genesis("testsupport"))
    testImplementation(genesis("pal-eventhandler"))
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:json-schema-validator:latest.release")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

    runtimeOnly(files("../../../docker/jacocoagent.jar"))
}

description = "permissions-app"

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources", "src/main/genesis")
        }
    }
}
