plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("groovy")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.0"
}

version = "0.1"
group = "com.codecanvas"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Otlp
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("ch.qos.logback:logback-classic")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("io.micronaut:micronaut-validation")

    implementation("io.opentelemetry:opentelemetry-api:1.22.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.22.0")
    implementation("io.opentelemetry:opentelemetry-semconv:1.22.0-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:1.22.0")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:1.22.0-alpha")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("io.vavr:vavr-kotlin:0.10.2")
}


application {
    mainClass.set("com.codecanvas.otlpclient.OtlpClientApp")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("com.codecanvas.*")
    }
}



