rootProject.name = "scs-mtom"

pluginManagement {
    val wsdl2javaPluginVersion: String by settings
    plugins {
        id("no.nils.wsdl2java") version wsdl2javaPluginVersion
    }
}
