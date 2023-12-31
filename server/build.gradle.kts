plugins {
	java
	application
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.diffplug.spotless") version "6.23.3"
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
	testImplementation("com.h2database:h2:2.2.224")
}

application {
	mainClass = "com.bill.server.ServerApplication"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Props to: https://jskim1991.medium.com/gradle-creating-a-new-source-set-for-integration-test-using-kotlin-dsl-f5b67da2aea3
val integrationTest: SourceSet = sourceSets.create("integrationTest") {
	java {
		compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
		runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
		srcDir("src/integration-test/java")
	}
	resources.srcDir("src/integration-test/resources")
}

configurations[integrationTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

val integrationTestTask = tasks.register<Test>("integrationTest") {
	group = "verification"

	useJUnitPlatform()

	testClassesDirs = integrationTest.output.classesDirs
	classpath = sourceSets["integrationTest"].runtimeClasspath

	shouldRunAfter("test")
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