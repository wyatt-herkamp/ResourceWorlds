plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "me.kingtux"
version = "1.0-SNAPSHOT"



repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.onarandombox.com/content/groups/public")
    maven("https://repo.potatocorp.dev/storages/maven/kingtux-repo")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly(group = "com.onarandombox.multiversecore", name = "Multiverse-Core", version = "4.2.2")
    implementation(group = "me.kingtux", name = "enumconfig", version = "1.0")
}

tasks.processResources {
    expand("version" to project.version)
}