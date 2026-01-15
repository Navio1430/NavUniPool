plugins {
  kotlin("jvm")
  alias(libs.plugins.shadow)
  alias(libs.plugins.blossom)
}

val projectName = "NavUniPool"

tasks.shadowJar {
  destinationDirectory.set(file("../target"))

  archiveBaseName.set("${projectName}-paper")
  archiveClassifier = null

  relocatePrefixed("org.kotlin")

  exclude("org/sfl4j/**")

  doLast {
    val targetDirEnv = System.getenv("TARGET_DIR") ?: return@doLast
    val pluginsDir = file(targetDirEnv)
    if (pluginsDir.exists() && pluginsDir.isDirectory) {
      pluginsDir.listFiles { _, name -> name.startsWith("$projectName-paper") }?.forEach {
        it.delete()
      }

      val builtJar = archiveFile.get().asFile
      copy {
        from(builtJar)
        into(pluginsDir)
      }
    } else {
      logger.warn("plugin target directory does not exist, skipping plugin copy to run directory")
    }
  }
}

var prefix = "pl.spcode.${projectName.lowercase()}.lib";
fun com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.relocatePrefixed(pkg: String) {
  relocate(pkg, "${prefix}.$pkg")
}

dependencies {
  implementation(project(":navunipool-common"))

  compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

kotlin {
  jvmToolchain(21)
}

// Templating properties like version and project name
tasks.withType<ProcessResources> {
  outputs.upToDateWhen { false }
  filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
    expand(
        mapOf(
            "version" to version,
            "apiVersion" to "1.20.4",
            "group" to rootProject.group
        )
    )
  }
}

