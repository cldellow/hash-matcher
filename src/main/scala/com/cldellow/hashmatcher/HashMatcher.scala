package com.cldellow.hashmatcher

import java.nio._

object HashMatcher {
  def createIntHash(needles: String*): (Array[Byte], Int) => Boolean = {
    val bits = math.ceil(math.log(needles.length + 1) / math.log(2)).toInt
    val slots = math.pow(2, bits).toInt

    require(needles.length == needles.toSet.size, s"duplicates aren't permitted")

    val shiftFactor = 32 - bits - 1
    val ints: Array[Int] = needles.map { needle =>
      val bytes = needle.getBytes("UTF-8")
      require(bytes.length == 4, s"expected string of exactly length 4: $needle")

      val int = ByteBuffer.wrap(bytes).asIntBuffer.get
      int
    }.toArray

    var keepGoing = true
    val ns = System.nanoTime
    var factor: Int = 1

    while(keepGoing) {
      val factors: Array[Int] = new Array[Int](slots)
      var slot = 0

      var success = true
      while(success && slot < ints.length) {
        val index = (((ints(slot) * factor) & 0x7FFFFFFF) >> shiftFactor)
        //println(s"val: ${ints(slot)} factor: $factor, shiftFactor: $shiftFactor, index: $index")
        if(factors(index) != 0) {
          success = false
        } else {
          factors(index) = ints(slot)
          slot = slot + 1
        }
      }

      if(success && factors(0) != 0) {
        keepGoing = false
        def f(xs: Array[Byte], length: Int): Boolean = {

          var i = 0
          while(i < 4 && length >= 4) {
            val ints = ByteBuffer.wrap(xs, i, length - i).asIntBuffer
            while(ints.hasRemaining) {
              val int = ints.get
              //println(s"testing: $int")
              if(factors(((int * factor) & 0x7FFFFFFF) >> shiftFactor) == int)
                return true
            }
            i = i + 1
          }

          return false
        }

        return f _
      } else {
        factor = factor + 1
      }

      if((System.nanoTime - ns) / 1e6 > 5000) {
        keepGoing = false
        sys.error("More than threshold seconds, bailing")
      }
    }

    sys.error("couldn't find it")
  }

}
