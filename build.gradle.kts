// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) version Versions.pluginAndroid apply false
    id(Plugins.androidLibrary) version Versions.pluginAndroid apply false
    id(Plugins.kotlinAndroid) version Versions.pluginKotlin apply false
    kotlin(Plugins.jvm) version Versions.serialization apply false
    kotlin(Plugins.kotlinSerialization) version Versions.serialization apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}