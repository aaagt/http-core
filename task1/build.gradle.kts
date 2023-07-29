plugins {
    application
}


application {
    mainClass.set("Main")
}


dependencies {
    val junitVersion: String by project

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}


tasks.test {
    useJUnitPlatform()
}
