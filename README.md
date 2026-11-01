# XeadModeler

## Gradle Build Setup

This project now uses Gradle with Kotlin DSL for building. The build is configured to use Java 21 and UTF-8 encoding for all source files to prevent character corruption in comments.

### Prerequisites

1. Java 21 JDK installed
2. Required JAR dependencies placed in the `libs` directory:
   - xercesImpl.jar
   - poi-3.10-FINAL-20140208.jar
   - poi-ooxml-3.10-FINAL-20140208.jar
   - poi-ooxml-schemas-3.10-FINAL-20140208.jar
   - dom4j-1.6.1.jar
   - xmlbeans-2.3.0.jar
   - batik-awt-util.jar
   - batik-dom.jar
   - batik-ext.jar
   - batik-svggen.jar
   - batik-util.jar
   - batik-xml.jar

You can copy these JAR files from your existing installation or download them from their respective sources.

### Building the Project

To build the project, run:

```bash
./gradlew build
```

### Running the Application

To run the application, use:

```bash
./gradlew run
```

### Creating a Distribution

To create a distributable package:

```bash
./gradlew jar
```

This will create a JAR file in the `build/libs` directory that includes all dependencies.

## Note on UTF-8 Encoding

All source files are now configured to use UTF-8 encoding to prevent character corruption in comments. This is enforced through:

1. The `build.gradle.kts` file setting `options.encoding = "UTF-8"` for all Java compilation tasks
2. The `gradle.properties` file setting system properties for file encoding
