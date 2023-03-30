import com.sun.jndi.toolkit.url.Uri
import org.apache.groovy.nio.extensions.NioExtensions.deleteDir
import java.nio.file.Paths

/**
 * A plugin to cleanup the template after it has been forked. It register a single `templateCleanup`
 * task that is designed to run from CI. It:
 * - renames the root project
 * - replaces the maven coordinates with coordinates based on the Github repository where the
 * template is forked
 * - changes the package name
 * - changes the Android application ID
 * - cleanups after itself by removing the Github action and this plugin
 */

tasks.register("templateCleanup") {
    doLast {
        val (project, packageName) = "replace_name_project" to "com.replace.package.project"

        file("settings.gradle.kts").replace(
            "rootProject.name = (\"StartupComposeTemplate\")",
            "rootProject.name = (\"$project\")"
        )
        file("buildSrc/src/main/java/AppConfiguration.kt").replace(
            "com.startup.compose.template",
            packageName
        )

        patchReadme()
        changePackageName(packageName)

        // cleanup the cleanup :)
        file("build.gradle.kts").replace(
            "    cleanup\n",
            ""
        )
        file("buildSrc/src/main/kotlin/cleanup.gradle.kts").delete()
        //deleteDir(Paths.get("screenshots/"))
        file("screenshots/").deleteRecursively()
    }
}

fun String.sanitized() = replace(Regex("[^A-Za-z0-9]"), "").toLowerCase()

fun File.replace(regex: Regex, replacement: String) {
    writeText(readText().replace(regex, replacement))
}

fun File.replace(oldValue: String, newValue: String) {
    writeText(readText().replace(oldValue, newValue))
}

fun patchReadme() {
    var featuresFound = false
    val existingReadme = file("README.md").readLines().mapNotNull {
        if (it.startsWith("## Features")) {
            featuresFound = true
        }
        if (!featuresFound) null else it
    }.joinToString("\n")

    file("README.md").writeText(existingReadme)
}

fun srcDirectories() = projectDir.listFiles()!!
    .filter { it.isDirectory && !(it.name == "buildSrc") }
    .flatMap { it.listFiles()!!.filter { it.isDirectory && it.name == "src" } }

fun changePackageName(packageName: String) {
    srcDirectories().forEach {
        it.walk().filter {
            it.isFile && (it.extension == "kt" || it.extension == "kts" || it.extension == "xml")
        }.forEach {
            it.replace("com.startup.compose.template", packageName)
        }
    }
    val dirPackage = packageName.replace(".", "/")
    srcDirectories().forEach {
        it.listFiles()!!.filter { it.isDirectory } // down to src/main
            .flatMap { it.listFiles()!!.filter { it.isDirectory } } // down to src/main/java
            .forEach {
                val newDir = File(it, dirPackage)
                newDir.parentFile.mkdirs()
                File(it, "com/startup/compose/template").renameTo(newDir)
                File(it, "com/startup").deleteRecursively()
            }
    }
}