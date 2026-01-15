plugins {
  kotlin("jvm")
  id("maven-publish")
}

group = "pl.spcode.navunipool"

dependencies {
  api("com.zaxxer:HikariCP:7.0.2")
  compileOnly("eu.okaeri:okaeri-configs-yaml-snakeyaml:6.0.0-beta.27")
  compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

publishing {
  publications {
    create<MavenPublication>("gpr") {
      from(components["java"])
    }
  }
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/Navio1430/NavUniPool")
      credentials {
        username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String? ?: ""
        password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key") as String? ?: ""
      }
    }
  }
}

kotlin {
  jvmToolchain(21)
}
