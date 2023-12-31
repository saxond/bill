plugins {
	java
	application
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.diffplug.spotless") version "5.16.0"
}

group = "com.bill"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
	mainClass = "com.bill.server.ServerApplication"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		eclipse()
		indentWithSpaces(4)
	}
}

afterEvaluate {
	tasks["test"].dependsOn("spotlessCheck")
}