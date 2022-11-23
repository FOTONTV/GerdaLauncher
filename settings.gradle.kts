rootProject.name = "GMCL1"
include(
    "GMCL",
    "GMCLCore",
    "GMCLTransformerDiscoveryService"
)

val minecraftLibraries = listOf("GMCLTransformerDiscoveryService")

for (library in minecraftLibraries) {
    project(":$library").projectDir = file("minecraft/libraries/$library")
}
