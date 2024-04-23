# History of JKIDL development

## Repos

* https://github.com/kbaseattic/typecomp
  * The original Perl KIDL type compiler. Subsumed into the kb-sdk.
* https://github.com/kbase/java_type_generator
  * The original Java KIDL type compiler. Subsumed into the kb-sdk.
* https://github.com/kbase/kb_sdk
* https://github.com/kbase/jars
  * Built jars from `java_type_generator` in `/lib/jars/kbase/kidl`

## Nomenclature

* JKIDL will refer to the java KIDL parser code, contained in the `us.kbase.kidl` and
  `us.kbase.jkidl` packages.
* Dates are in YY/MM/DD format.

## Historical points of interest

* `java_type_generator`
  * `7863aef` - 14/08/28
    * This is the version of JKIDL used in https://github.com/kbase/workspace_deluxe (as of
      24/4/20)
    * https://github.com/kbase/jars/blob/master/lib/jars/kbase/kidl/kbase-kidl-parser-1409261812-7863aef.jar
      contains the code.
      * This is the newest version of the the jar in the `jars` repo.
  * `d44932` - 14/09/18
    * `master` branch
    * Significantly behind `7863aef`, but adds a license
    * Aware of no uses of this commit.
  * `ca4c24b1` - 15/04/13
    * Last commit
    * Contains all code from `7863aef`
    * Changes from `7863aef`:
      * Minor changes to processing comments wrt asterisks
      * Adds js/py/pl template information extraction to KIDL classes
        * This code should probably live elsewhere. The KIDL code should focus on
          parsing and regenerating the spec, not producing templates.
      * Fixed a bug that disallowed `required`, `optional`, and `none` as variable
        names.
      * Adds the async keyword for functions, which TMK is unused and should probably
        be removed.
* `kb_sdk`
  * `b77a4f4` - 15/04/13
    * Initial commit other than a prior license & readme commit.
    * Contains the JKIDL code - identical to `java_type_generator` `ca4c24b1`
  * `e29e537` - 15/05/21
    * `src/us` moved to `src/java/us`
    * No changes to JKIDL in commit:
      https://github.com/kbase/kb_sdk/commit/e29e537
    * No changes to JKIDL since `b77a4f4`
  * `80aebc4` - 18/12/04
    * `master` as of 24/4/20
    * version 1.2.1
    * Changes since `e29e537`:
      * Added documentation to python clients & servers
        * Not very readable, frankly - it's a giant block of text
      * Adds recognition of `@deprecated` annotation
      * Adds the visitor pattern to the parsed KIDL structure
        * Moves the `toJSON()` functionality to the `JSONableVisitor` class
        * Adds a HTMLGenVisitor class that converts the spec to an HTML document
      * Other minor changes
  * `3a7e1b2` - 23/03/15
    * `develop` as of 24/4/20
    * No changes to JKIDL since `80aebc4`