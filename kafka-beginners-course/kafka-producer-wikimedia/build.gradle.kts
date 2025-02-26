plugins {
    id("java")
}

group = "org.pjff.springcloud.msvc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //Vid 58, agregamos las dependencias

    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation ("org.apache.kafka:kafka-clients:3.1.0")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation ("org.slf4j:slf4j-api:1.7.32")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation ("org.slf4j:slf4j-simple:1.7.32")

    // https://mvnrepository.com/artifact/com.launchdarkly/okhttp-eventsource
    implementation ("com.launchdarkly:okhttp-eventsource:2.5.0")

    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

   // testImplementation(platform("org.junit:junit-bom:5.10.0"))
   // testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}