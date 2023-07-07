import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://mvnrepository.com/artifact/com.stripe/stripe-java
    implementation("com.stripe:stripe-java:22.6.0-beta.2")



//	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
//	implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
//	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-data-rest
//	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.12")
//// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-kotlin
//	runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.12")

    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger2
    implementation("io.springfox:springfox-swagger2:3.0.0")
// https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
// https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter
    implementation("io.springfox:springfox-boot-starter:3.0.0")



    runtimeOnly("org.postgresql:postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
