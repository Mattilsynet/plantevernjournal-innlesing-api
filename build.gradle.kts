plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

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
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jetbrains
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Serialisering:
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // Springdoc (Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    // gcp
    implementation(platform("com.google.cloud:spring-cloud-gcp-dependencies:7.4.0"))
    implementation("com.google.cloud:spring-cloud-gcp-starter-secretmanager")

    // nats og fisk
    implementation(platform("no.mattilsynet.fisk.libs:virtual-nats-bom:2025.10.01-08.55-50408cd916c0"))
    implementation("no.mattilsynet.fisk.libs:nats")
    implementation("no.mattilsynet.fisk.libs:spring")
    implementation("no.mattilsynet.fisk.libs:virtual-nats")

    // nats
    implementation("io.nats:jnats")

    // Testing:
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
