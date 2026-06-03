plugins {
    id("java-gradle-plugin")
    `maven-publish`
}

group = "com.breakout.plugin"
version = "1.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

gradlePlugin {
    plugins {
        create("breakoutTools") {
            id = "com.breakout.tools"
            implementationClass = "com.game.BreakoutHelperPlugin"
        }
    }
}