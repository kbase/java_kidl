# 0.2.0

* Updated to code from `kb_sdk_plus` commit `166fe43`.
* It is intended that KIDL parser development now takes place in this repo rather than
  `kb_sdk_plus`.
* `kb_sdk_plus`'s code was sourced from `kb_sdk` commit `80aebc4` and updated with the
  following changes:
    * The unused `async` keyword was removed.
    * Code for generating template data structures was moved into `kb_sdk_plus` proper with
      the rest of the templating code.
    * Other code not involved in parsing KIDL was moved into `kb_sdk_plus` proper.
    * For changes between `0.1.0` and `kb_sdk` `80aebc4`, see
      the [history of KIDL development](./JKIDL_HISTORY.md).

# 0.1.0

* Initial release
* Equivalent to commit `7863aef` of `java_type_compiler`
* Equivalent to jar `kbase-kidl-parser-1409261812-7863aef.jar` in the `kbase` folder in 
  https://github.com/kbase/jars
