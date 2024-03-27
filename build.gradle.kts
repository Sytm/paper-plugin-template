plugins {
  // Apply the java-library plugin for API and implementation separation.
  java
  with(libs.plugins) {
    alias(runPaper)
    alias(spotless)

    // alias(shadow)

    // alias(minotaur)
    // alias(changelog)
  }
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies { implementation(libs.paper) }

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

tasks {
  compileJava { options.encoding = "UTF-8" }

  /*
  shadowJar {
    archiveClassifier = ""

    minimize {
      exclude(dependency(libs.<>.get()))
    }

    exclude("META-INF/")

    dependencies {
      include(dependency(libs.<>.get()))
    }

    relocate("<>", "<>")
  }
  */

  processResources {
    val properties =
        mapOf(
            "name" to project.name,
            "version" to project.version,
        )

    inputs.properties(properties)

    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") { expand(properties) }
  }
}

spotless {
  java {
    palantirJavaFormat().style("GOOGLE")
    formatAnnotations()
  }
  kotlinGradle { ktfmt() }
}

/*
 modrinth {
   val modrinthToken: String? by project

   token = modrinthToken

   projectId = "<id>"
   versionType = "release"
   uploadFile.set(tasks.shadowJar)

   gameVersions.addAll("1.20.1")
   loaders.addAll("paper")

   changelog.set(
       provider {
         with(project.changelog) {
           renderItem(getLatest().withEmptySections(false).withHeader(false))
         }
       })

   debugMode = true
 }
  */
