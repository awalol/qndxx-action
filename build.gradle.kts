plugins {
    java
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "cn.awalol"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "cn.awalol.Main"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.httpcomponents","httpclient","4.5.13")
    implementation("com.fasterxml.jackson.core","jackson-core","2.12.1")
    implementation("com.fasterxml.jackson.core","jackson-annotations","2.12.1")
    implementation("com.fasterxml.jackson.core","jackson-databind","2.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}