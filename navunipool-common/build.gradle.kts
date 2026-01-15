plugins {
  kotlin("jvm")
}

dependencies {
  api(project(":navunipool-api"))

  // database
  api("com.zaxxer:HikariCP:7.0.2")

  // drivers
  runtimeOnly("com.mysql:mysql-connector-j:9.5.0")
  runtimeOnly("org.postgresql:postgresql:42.7.8")

  // config
  implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:6.0.0-beta.27")

  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(21)
}

tasks.test {
  useJUnitPlatform()
}