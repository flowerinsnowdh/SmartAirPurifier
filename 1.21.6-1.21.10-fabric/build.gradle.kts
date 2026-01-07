plugins {
	alias(libs.plugins.fabric.loom.remap)
	id("maven-publish")
}

version = "${project.property("mod_version")}"
group = "${project.property("maven_group")}"

base {
	archivesName = "${project.property("archives_base_name")}"
}

repositories {
	maven("https://maven.terraformersmc.com/") {
		content {
			includeModule("com.terraformersmc", "modmenu")
		}
	}
	System.getenv("GRADLE_CENTRAL_MIRROR")?.let {
		maven(it)
	}
	mavenCentral()
}

dependencies {
	minecraft(libs.minecraft)
	mappings(libs.yarn) {
		artifact {
			classifier = "v2"
		}
	}
	modImplementation(libs.fabric.loader)
	modImplementation(libs.fabric.api)
	modImplementation(libs.modmenu)

	compileOnly(libs.jetbrains.annotations)

	include(libs.jackson.annotations)
	implementation(libs.jackson.annotations)
	include(libs.jackson.core)
	implementation(libs.jackson.core)
	include(libs.jackson.databind)
	implementation(libs.jackson.databind)
}

tasks.processResources {
	val replaceProperties = mapOf(
		"version" to project.version
	)
	replaceProperties.forEach(inputs::property)

	filesMatching("fabric.mod.json") {
		expand(replaceProperties)
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.jar {
	inputs.property("archivesName", project.base.archivesName)

	from("../LICENSE")
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			artifactId = "${project.property("archives_base_name")}"
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}