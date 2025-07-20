plugins {
    id("org.openrewrite.build.recipe-library-base") version "2.0.3"

    // This uses the nexus publishing plugin to publish to the moderne-dev repository
    // Remove it if you prefer to publish by other means, such as the maven-publish plugin
//    id("org.openrewrite.build.publish") version "latest.release"
    id("nebula.release") version "20.2.0" // Pinned as v21+ requires Gradle 9+

    // Configures artifact repositories used for dependency resolution to include maven central and nexus snapshots.
    // If you are operating in an environment where public repositories are not accessible, we recommend using a
    // virtual repository which mirrors both maven central and nexus snapshots.
    id("org.openrewrite.build.recipe-repositories") version "2.0.3"
}

group = "co.uk.rewrite.recipe.avijit"
description = "Rewrite recipes for Java21"

dependencies {
    // The bom version can also be set to a specific version
    // https://github.com/openrewrite/rewrite-recipe-bom/releases
    implementation(platform("org.openrewrite.recipe:rewrite-recipe-bom:3.11.1"))

    implementation("org.openrewrite:rewrite-java")
    implementation("org.openrewrite.recipe:rewrite-java-dependencies")
    implementation("org.openrewrite:rewrite-yaml")
    implementation("org.openrewrite.meta:rewrite-analysis")
    implementation("org.assertj:assertj-core:latest.release")
    runtimeOnly("org.openrewrite:rewrite-java-17")

    // Refaster style recipes need the rewrite-templating annotation processor and dependency for generated recipes
    // https://github.com/openrewrite/rewrite-templating/releases
    annotationProcessor("org.openrewrite:rewrite-templating:1.29.2")
    implementation("org.openrewrite:rewrite-templating")
    // The `@BeforeTemplate` and `@AfterTemplate` annotations are needed for refaster style recipes
    compileOnly("com.google.errorprone:error_prone_core:2.40.0") {
        exclude("com.google.auto.service", "auto-service-annotations")
        exclude("io.github.eisop","dataflow-errorprone")
    }

    // The RewriteTest class needed for testing recipes
    testImplementation("org.openrewrite:rewrite-test") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
    }

    // Need to have a slf4j binding to see any output enabled from the parser.
    runtimeOnly("ch.qos.logback:logback-classic:1.5.18")

    // Our recipe converts Guava's `Lists` type
    testRuntimeOnly("com.google.guava:guava:33.4.8-jre")
    testRuntimeOnly("org.apache.commons:commons-lang3:3.18.0")
    testRuntimeOnly("org.springframework:spring-core:6.2.9")
    testRuntimeOnly("org.springframework:spring-context:6.2.9")
}
