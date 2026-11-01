plugins {
    java
    application
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "xeadModeler"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<JavaCompile> {
    // Note: This project contains source files with various encodings (likely Shift_JIS for Japanese text,
    // EUC-KR for Korean text, etc.). To properly build this project, each file would need to be
    // converted to UTF-8 or the appropriate encoding would need to be specified for each file.
    // For now, we're using UTF-8 as the default encoding, but this will cause compilation errors
    // for files with non-UTF-8 characters.
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")

    // SLF4J と Logback (ロギング)
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("org.slf4j:slf4j-api:2.0.16")

    // テスト依存関係
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Xerces XML - Updated to 2.12.2 (latest stable)
    implementation("xerces:xercesImpl:2.12.2")

    // Apache POI (Excel support) - Updated to 5.2.5 for Java 11+ compatibility and security fixes
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.poi:poi-ooxml-lite:5.2.5")
    implementation("org.apache.xmlbeans:xmlbeans:5.1.1")

    // Material UI Swing - Material Design Look and Feel
    implementation("io.github.vincenzopalazzo:material-ui-swing:1.1.2")

    // Apache Batik (SVG support) - Updated to 1.18
    implementation("org.apache.xmlgraphics:batik-awt-util:1.18")
    implementation("org.apache.xmlgraphics:batik-dom:1.18")
    implementation("org.apache.xmlgraphics:batik-ext:1.18")
    implementation("org.apache.xmlgraphics:batik-svggen:1.18")
    implementation("org.apache.xmlgraphics:batik-util:1.18")
    implementation("org.apache.xmlgraphics:batik-xml:1.18")
    implementation("org.apache.xmlgraphics:batik-svg-dom:1.18")
    implementation("org.apache.xmlgraphics:batik-gvt:1.18")
    implementation("org.apache.xmlgraphics:batik-transcoder:1.18")
}

application {
    mainClass.set("xeadModeler.XeadModelerApplication")
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.bootJar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to "xeadModeler.Application"
        )
    }
}

tasks.jar {
    enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("XeadModeler-${project.version}.jar")
}

tasks.test {
    useJUnitPlatform()
}
