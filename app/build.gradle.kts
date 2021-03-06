plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"

    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("io.mockk:mockk:1.10.6")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

sourceSets.create("common") {
    java.srcDir("src/common/kotlin")
}

sourceSets.test {
    compileClasspath += sourceSets["common"].output
    runtimeClasspath += sourceSets["common"].output
}

sourceSets.create("acceptance") {
    java.srcDir("src/acceptance/kotlin")
    resources.srcDir("src/acceptance/resources")
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
    compileClasspath += sourceSets["common"].output
    runtimeClasspath += sourceSets["common"].output
}

task<Test>("acceptance") {
    description = "Runs the acceptance tests"
    group = "verification"
    testClassesDirs = sourceSets["acceptance"].output.classesDirs
    classpath = sourceSets["acceptance"].runtimeClasspath
    configurations["acceptanceImplementation"].extendsFrom(configurations.testImplementation.get())
    mustRunAfter(tasks["test"])
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

application {
    mainClass.set("com.pankaj.parking.AppKt")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
    standardOutput = System.`out`
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}