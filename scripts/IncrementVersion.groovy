includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    depends(checkVersion, parseArguments)

    def oldVersion = metadata.'app.version'
    String[] parts = oldVersion?.split("\\.")
    Integer lastPart = Integer.parseInt(parts[parts.length -1])
    lastPart++
    parts[parts.length - 1] = lastPart
    println "New version: " + parts.join(".")
    metadata.'app.version' = parts.join(".")
    metadata.persist()

}

setDefaultTarget(main)
