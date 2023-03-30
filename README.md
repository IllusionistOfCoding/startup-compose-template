# kotlin-android-template 🤖

A simple Github template that lets you create an **Android/Kotlin** project and be up and running in a **few seconds**.

## How to use 👣

Just click on [![Use this template](https://img.shields.io/badge/-Use%20this%20template-brightgreen)](https://github.com/DualBit/it-android-jetpack-template/generate) button to create a new repo starting from this template.
Steps to run:
- Create a new repository on Github by clicking on [![Use this template](https://img.shields.io/badge/-Use%20this%20template-brightgreen)](https://github.com/DualBit/it-android-jetpack-template/generate) button.
- Clone the new project.
- On Android Studio: Settings -> Experimental -> remove the flag "Only include test tasks in the Gradle task list generated during Gradle Sync".
- sostituisco i campi projectName e packagename in template-cleanup.gradle.kts
- build il progetto
- lancio lo script template-cleanup
- sync project with gradle files
- ed finito 

Once created don't forget to update the:
- [App ID](buildSrc/src/main/java/Coordinates.kt)
- AndroidManifest ([here](app/src/main/AndroidManifest.xml) and [here](library-android/src/main/AndroidManifest.xml))
- Package of the source files

## Features 🎨

- **100% Kotlin-only template**.
- Jetpack Compose setup ready to use.
- 100% Gradle Kotlin DSL setup.
- Dependency versions managed via `buildSrc`.

## Gradle Setup 🐘

This template is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.