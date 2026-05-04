plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    kotlin("plugin.serialization") version "2.3.21"
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
val mockitoAgent = configurations.create("mockitoAgent")

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Mattilsynet/fisk/")
        credentials {
            username = "token"
            password = System.getenv("READ_SOURCE_AND_PACKAGES")
        }
    }
}

dependencies {
    // Spring boot:
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Spring webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Jetbrains
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Serialisering:
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    // Springdoc (Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:3.0.3")

    // gcp
    implementation(platform("com.google.cloud:spring-cloud-gcp-dependencies:8.0.2"))
    implementation("com.google.cloud:spring-cloud-gcp-starter-secretmanager")

    // Jackson:
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // GeoJson:
    implementation("org.locationtech.jts.io:jts-io-common:1.20.0")
    implementation("org.wololo:jts2geojson:0.18.1")

    // virtual-nats
    implementation(platform("no.mattilsynet.virtualnats:virtual-nats-bom:2026.04.17-14.45-035afbfcc209"))
    implementation("no.mattilsynet.virtualnats:virtual-nats-core")
    implementation("no.mattilsynet.virtualnats:virtual-nats-spring")

    // nats
    implementation("io.nats:jnats")

    // Testing:
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")
    mockitoAgent("org.mockito:mockito-core") { isTransitive = false }

    testImplementation("org.springframework.boot:spring-boot-webtestclient")
}

kotlin {
    compilerOptions {
        // -Xannotation-default-target=param-property fjerner advarsel om annoteringer. Kan muligens fjernes fra Kotlin
        // versjon 2.3
        compilerOptions.freeCompilerArgs = listOf("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks {

    bootJar {
        archiveFileName.set("app.jar")
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
