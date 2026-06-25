plugins {
	java
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sdewa"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.security:spring-security-webauthn")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	
	// Test Dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("com.fasterxml.jackson.core:jackson-databind")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
	
	// JWT Dependencies (JJWT)
	// Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	implementation("io.jsonwebtoken:jjwt-api:0.13.0")
	// Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
	// Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

	implementation("com.mysql:mysql-connector-j")

	testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
