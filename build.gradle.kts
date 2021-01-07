plugins {
    id("java")
}

group = "me.kingtux"
version = "1.0-SNAPSHOT"



repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.onarandombox.com/content/groups/public")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly(group = "com.onarandombox.multiversecore", name = "Multiverse-Core", version = "4.2.2")
}

tasks.processResources {
    expand("version" to project.version)
}