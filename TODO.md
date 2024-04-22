* Update this code with the most recent code from kb_sdk.
  * Before the update, the template data extraction code (e.g. the `forTemplates()` method
    and dependencies) should be removed from the KIDL parser code. The code should be for parsing
    and regenerating KIDL files, not handling templating.
  * Similarly, the HTML generation code should be moved into the KIDL code, perhaps in
    `kidlhtml` or `kidl.html`.
* Possibly move everything under the `kidl` namespace, including `jkidl`.
* Update the javacc jar to something from maven & add Gradle target to compile the spec parser.
  * Compilation probably never needs to be done again, so it can wait.