plugins {
  id("com.gradleup.shadow") version "9.3.0"
}

configurations.all {
  resolutionStrategy {
    force("com.google.guava:guava:33.6.0-jre")
    force("com.google.code.gson:gson:2.14.0")
  }
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:26.3.build.7-alpha")
  implementation("net.kyori:adventure-platform-bukkit:4.3.4")
  implementation("net.kyori:adventure-text-minimessage:4.17.0")
  compileOnly("net.dmulloy2:ProtocolLib:5.4.0")
  compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.17")
}

tasks.shadowJar {
  archiveClassifier.set("")
  archiveVersion.set("")
  archiveBaseName.set("InventoryStacks")
  duplicatesStrategy = DuplicatesStrategy.INCLUDE

  relocate("net.kyori", "me.szabee.inventorystacks.libs.net.kyori")

  exclude("META-INF/MANIFEST.MF")

  mergeServiceFiles()
}

tasks.jar {
  finalizedBy(tasks.shadowJar)
}

tasks.processResources {
  filesMatching("plugin.yml") {
    expand("version" to project.version)
  }
}
