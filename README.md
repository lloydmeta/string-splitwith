# string-splitwith

Started off as an exercise to re-implement String#split without regexp. 

Currently 25% faster than String#split.

## Usage

```scala
import scala.ext.string.Splitting._

assert("hello.world.yay".splitWith("ello") == Seq("h", ".world.yay"))
```

## Rules

* Idiomatic Scala code
* Must be tested