# hash-matcher

Implements a constant time needle-in-haystack search over byte arrays, using the ideas
from http://pzemtsov.github.io/2016/10/16/custom-hash-function.html

This library is useful for quickly pre-filtering corpora to decide whether to do
further processing.

## Usage


```scala
import com.cldellow.hashmatcher.HashMatcher.createIntHash

val f = createIntHash("food", "good")

f("People eat food".getBytes("UTF-8"), 15) // true

f("Word!".getBytes("UTF-8"), 5) // false
```
