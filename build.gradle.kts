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
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly(group = "com.onarandombox.multiversecore", name = "Multiverse-Core", version = "4.2.2")
    implementation(group = "me.kingtux", name = "enumconfig", version = "1.0")
    implementation("org.bstats:bstats-bukkit:1.8")
}
tasks {
    "shadowJar"(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        dependencies {
            relocate("org.bstats.bukkit","me.kingtux.secondend.bstats")
        }
    }
}
tasks.processResources {
    expand("version" to project.version)
}