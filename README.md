# Java KIDL Parser

This repo contains code in Java for parsing KBase Interface Definition files. Generally speaking,
you won't use this library directly, but as part of https://github.com/kbase/workspace_deluxe
or https://github.com/kbase/kb_sdk.

## Including the client in your build

See https://jitpack.io/#kbase/java_kidl for instructions on how to include JitPack
built dependencies in your build.

## JavaDoc

JavaDoc is available at
```
https://javadoc.jitpack.io/com/github/kbase/java_kidl/<version>/javadoc/
```

For example:

https://javadoc.jitpack.io/com/github/kbase/java_kidl/0.1.0/javadoc/

## Development

### Adding and releasing code

* Adding code
  * All code additions and updates must be made as pull requests directed at the develop branch.
    * All tests must pass and all new code must be covered by tests.
    * All new code must be documented appropriately
      * Javadoc
      * General documentation if appropriate
      * Release notes
* Releases
  * The main branch is the stable branch. Releases are made from the develop branch to the main
    branch.
  * Tag the version in git and github.
  * Create a github release.
  * Check that the javadoc is appropriately built on JitPack.

### Testing

```
./gradlew test
```

## Other Java KIDL code locations

See [history](JKIDL_HISTORY.md).
