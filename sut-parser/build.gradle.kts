
val kotlin_version: String by project

plugins {
    id("library-plugin")
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    api("org.jsoup:jsoup:1.11.3")
    implementation("junit:junit:4.13")
}