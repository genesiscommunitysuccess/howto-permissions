plugins {
    id("global.genesis.bdd_automation") version "3.5.76"
}

dependencies {
    compileOnly(genesis("script-dependencies"))
    genesisGeneratedCode(withTestDependency = true)
    testImplementation(genesis("dbtest"))
    testImplementation(genesis("testsupport"))
    testImplementation(genesis("pal-eventhandler"))
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:json-schema-validator:6.3.0")
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

// Genesis's Groovy 3 clashes with the BDD library's Groovy 4
configurations {
    testCompileClasspath { exclude(group = "org.codehaus.groovy") }
    testRuntimeClasspath { exclude(group = "org.codehaus.groovy") }
}
