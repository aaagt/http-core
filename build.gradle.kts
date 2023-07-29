plugins {
    id("java")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    tasks.withType<JavaCompile>().configureEach {
        //options.compilerArgs.add("--enable-preview")
        javaCompiler.set(javaToolchains.compilerFor {
            languageVersion.set(JavaLanguageVersion.of(17))
        })
    }
}
