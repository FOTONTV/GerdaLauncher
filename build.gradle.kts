plugins {
    id("checkstyle")
}

group = "fun.fotontv"
version = "1.0"

subprojects {
    apply {
        plugin("java")
        plugin("idea")
        plugin("checkstyle")
    }

    repositories {
        flatDir {
            name = "libs"
            dirs = setOf(rootProject.file("lib"))
        }
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"

        options.encoding = "UTF-8"
    }

    configure<CheckstyleExtension> {
        sourceSets = setOf()
    }

    tasks.withType<Checkstyle> {
        exclude("de/javawi/jstun")
    }

    dependencies {
        "testImplementation"("junit:junit:4.12")
    }
}

tasks.create("checkTranslations") {
    doLast {
        val gmclLangDir = file("GMCL/src/main/resources/assets/lang")

        val ru = java.util.Properties().apply {
            gmclLangDir.resolve("I18N.properties").bufferedReader().use { load(it) }
        }

        val en = java.util.Properties().apply {
            gmclLangDir.resolve("I18N_en.properties").bufferedReader().use { load(it) }
        }

        var success = true

        en.forEach {
            if (!ru.containsKey(it.key)) {
                project.logger.warn("I18N.properties missing key '${it.key}'")
                success = false
            }
        }

        if (!success) {
            throw GradleException("Part of the translation is missing")
        }
    }
}

apply {
    from("javafx.gradle.kts")
}

defaultTasks("clean", "build")