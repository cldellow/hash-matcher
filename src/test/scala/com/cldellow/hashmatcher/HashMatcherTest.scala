package com.cldellow.hashmatcher

import org.scalatest._
import java.nio._

class HashMatcherTest extends FlatSpec with Matchers {
  "create matchers" should "work" in {
    val f = HashMatcher.createIntHash("food")

    f("food".getBytes("UTF-8"), 4) should be (true)
    f("xfood".getBytes("UTF-8"), 5) should be (true)
    f("xxfood".getBytes("UTF-8"), 6) should be (true)
    f("xxxfood".getBytes("UTF-8"), 7) should be (true)
    f("xxxxfood".getBytes("UTF-8"), 8) should be (true)

    f("good".getBytes("UTF-8"), 4) should be (false)
    f("xgood".getBytes("UTF-8"), 5) should be (false)
    f("xxgood".getBytes("UTF-8"), 6) should be (false)
    f("xxxgood".getBytes("UTF-8"), 7) should be (false)
    f("xxxxgood".getBytes("UTF-8"), 8) should be (false)
  }

  "create multiple matchers" should "work" in {
    val f = HashMatcher.createIntHash("food", "good")

    f("food".getBytes("UTF-8"), 4) should be (true)
    f("xfood".getBytes("UTF-8"), 5) should be (true)
    f("xxfood".getBytes("UTF-8"), 6) should be (true)
    f("xxxfood".getBytes("UTF-8"), 7) should be (true)
    f("xxxxfood".getBytes("UTF-8"), 8) should be (true)

    f("good".getBytes("UTF-8"), 4) should be (true)
    f("xgood".getBytes("UTF-8"), 5) should be (true)
    f("xxgood".getBytes("UTF-8"), 6) should be (true)
    f("xxxgood".getBytes("UTF-8"), 7) should be (true)
    f("xxxxgood".getBytes("UTF-8"), 8) should be (true)
  }


  "create duplicate matchers" should "fail" in {
    assertThrows[IllegalArgumentException] {
      HashMatcher.createIntHash("food", "food")
    }
  }

  "create non-four letter matcher" should "fail" in {
    assertThrows[IllegalArgumentException] {
      HashMatcher.createIntHash("foo")
    }
  }

}
