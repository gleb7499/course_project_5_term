plugins {
    alias(libs.plugins.android.application)
    id("org.sonarqube") version "5.1.0.4882"
    id("com.google.gms.google-services")
}

sonar {
    properties {
        property("sonar.projectKey", "gleb7499_course-project-5-term")
        property("sonar.organization", "gleb7499")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

android {
    namespace = "com.example.lifeline"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lifeline"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        xmlReport = true
        htmlReport = true
        checkAllWarnings = true
        abortOnError = true
        checkDependencies = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)

    testImplementation(libs.junit)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}