plugins {
  java
}

allprojects {
  group = "me.szabee.inventorystacks"
  version = "4.0.0"

  repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
  }
}

subprojects {
  apply(plugin = "java")

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(26))
    }
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }
}
