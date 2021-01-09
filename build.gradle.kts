plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    `java-library`
    `maven-publish`
    signing
}

group = "me.kingtux"
version = "1.0-SNAPSHOT"
val artifactName = "ResourceWorlds"

java {
    withJavadocJar()
    withSourcesJar()
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11

}
repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.onarandombox.com/content/groups/public")
    maven("https://repo.potatocorp.dev/storages/maven/kingtux-repo")
    maven("https://repo.codemc.org/repository/maven-public")
    maven("https://jitpack.io")

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly(group = "com.onarandombox.multiversecore", name = "Multiverse-Core", version = "4.2.2")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    implementation(group = "me.kingtux", name = "enumconfig", version = "1.0")
    implementation("org.bstats:bstats-bukkit:1.8")
    implementation(group = "org.apache.commons", name = "commons-text", version = "1.9")
}
tasks {
    "shadowJar"(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        dependencies {
            relocate("org.bstats.bukkit", "me.kingtux.secondend.bstats")
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {

            artifactId = artifactName
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(artifactName)
            }
        }
    }
    repositories {
        maven {

            val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
        mavenLocal()
    }
}


tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.processResources {
    expand("version" to project.version)
}