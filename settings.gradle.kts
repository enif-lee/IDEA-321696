rootProject.name = "platform-service"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":subproject:domain",
    ":subproject:application",
    ":subproject:infrastructure",
    ":subproject:interface",
    ":subproject:interface:platform",
    ":subproject:boot",
    ":subproject:boot:api",
    ":subproject:boot:payout-batch",
    ":subproject:boot:payout-processor"
)
