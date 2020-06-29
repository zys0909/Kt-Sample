import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionManager : Plugin<Project> {
    override fun apply(project: Project) {

    }

    companion object {
        const val compileSdk = 29
        const val buildSdk = "29.0.3"
        const val minSdk = 21
        const val targetSdk = 29
        const val kotlin_version = "1.3.71"
        const val arouter_kapt = "com.alibaba:arouter-compiler:1.2.2"
    }
}