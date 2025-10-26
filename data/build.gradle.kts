import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.ieum.data"
    compileSdk = 35
    
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        buildConfigField("String", "SGIS_CONSUMER_KEY", properties.getProperty("SGIS_CONSUMER_KEY"))
        buildConfigField("String", "SGIS_CONSUMER_SECRET", properties.getProperty("SGIS_CONSUMER_SECRET"))

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":domain"))

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ktor
    implementation(libs.bundles.ktor)

    // kotlinx-serialization
    implementation(libs.kotlinx.serialization)

    // datastore
    implementation(libs.bundles.datastore)

    // timber
    implementation(libs.timber)

    // paging-common
    implementation(libs.androidx.paging3.common)
}