

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.6.21"
}


val PUBLISH_GROUP_ID: String by extra("se.warting.permissionsui")
val PUBLISH_VERSION: String by extra(rootProject.version as String)
val PUBLISH_ARTIFACT_ID by extra("permissionsui")

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")

val composeVersion = "1.3.0-beta01"

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check"
        )
    }

    lint {
        baseline = file("lint-baseline.xml")
        checkReleaseBuilds = true
        checkAllWarnings = true
        warningsAsErrors = true
        abortOnError = true
        disable.add("GradleDependency")
        disable.add("TypographyQuotes")
        checkDependencies = true
        checkGeneratedSources = false
        sarifOutput = file("../lint-results-permissionui.sarif")
    }
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
}


dependencies {


    val coroutineVersion = "1.6.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    implementation("dev.marcelpinto:permissions-compose-ktx:0.9")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.ui:ui-text:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    api("androidx.startup:startup-runtime:1.1.1")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}


