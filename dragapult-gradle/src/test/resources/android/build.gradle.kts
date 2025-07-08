plugins {
    id("com.android.application") version "8.9.2"
    id("io.github.diareuse.dragapult")
}

repositories {
    google()
}

android {
    namespace = "app.dragapult"
    compileSdk = 35
    defaultConfig {
    }
}

dragapult {
    local("default") {
        this.file = project.file("input.json")
    }
}