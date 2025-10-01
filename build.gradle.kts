plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring boot:
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Jetbrains
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Springdoc (Swagger)
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.10")

	// Testing:
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
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
