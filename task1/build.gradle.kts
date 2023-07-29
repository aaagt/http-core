plugins {
    application
}


application {
    mainClass.set("Main")
}


dependencies {
    val junitVersion: String by project
    val httpclientVersion: String by project
    val jacksonVersion: String by project

    // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
    implementation("org.apache.httpcomponents:httpclient:$httpclientVersion")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // tests
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}


tasks.test {
    useJUnitPlatform()
}
