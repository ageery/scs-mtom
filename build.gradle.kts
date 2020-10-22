plugins {
    java
    application
    id("no.nils.wsdl2java")
}

repositories {
    mavenCentral()
}

val apacheCxfVersion: String by project
val wsdl2javaPluginVersion: String by project
val baseScsUrl: String by project

val localWsdlDir = "$projectDir/src/main/resources/wsdl"
val generatedDir = "$projectDir/generated/src/main/java"

val wsdlList = listOf("SignatureAndCorService2")

wsdl2java {
    wsdlDir = file(localWsdlDir)
    wsdlsToGenerate = wsdlList.map {
        listOf(
                "-client",
                "-verbose",
                "-wsdlLocation",
                "$baseScsUrl/$it?wsdl",
                "$localWsdlDir/$it.wsdl"
        )
    }
}

application {
    mainClass.set("org.example.SCSAuthOverMTOM")
    applicationDefaultJvmArgs = listOf(
            "-Dcom.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true",
            "-Dcom.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump=true",
            "-Dcom.sun.xml.ws.transport.http.HttpAdapter.dump=true",
            "-Dcom.sun.xml.internal.ws.transport.http.HttpAdapter.dump=true"
    )
}

tasks.create("cleanGenerated") {
    doLast {
        delete(generatedDir)
    }
    dependsOn("clean")
}
