// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    https://mvnrepository.com/artifact/com.google.dagger/hilt-android
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
//    https://github.com/google/ksp/releases
    id("com.google.devtools.ksp") version "2.2.20-2.0.4" apply false
}