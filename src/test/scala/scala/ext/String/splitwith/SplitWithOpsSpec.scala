package scala.ext.string.splitwith

import org.scalatest.{Matchers, FunSpec}

class SplitWithOpsSpec extends FunSpec with Matchers {

  describe("#splitWith") {

    describe("char splitter") {

      it("should return Nil for an empty string") {
        "".splitWith('.') should be (Seq.empty)
      }
      it("should return a sequence with an empty string when the string contains just the separator"){
        ".".splitWith('.') should be (Seq(""))
      }
      it("should properly manage regexp chars"){
        "hello.world.yay".splitWith('.') should be(List("hello", "world", "yay"))
      }

    }

    describe("string splitter") {

      it("should return Nil for an empty string") {
        "".splitWith("hi") should be (Seq.empty)
      }
      it("should return a sequence with an empty string when the string contains just the separator"){
        "hi".splitWith("hi") should be (Seq(""))
      }
      it("should properly manage regexp chars in the separator string"){
        "hello.world.yay".splitWith(".w") should be(List("hello", "orld.yay"))
      }
      it("should work with a simple separator string") {
        "hello.world.yay".splitWith("ello") should be (Seq("h", ".world.yay"))
      }

    }
  }

  describe("performance check") {

    def bench(f: => Unit, times: Int = 500000): Long = {
      (0 to 500000).foreach(_ => f) // warm up
      val start = System.currentTimeMillis()
      (0 to times).foreach(_ => f)
      System.currentTimeMillis() - start
    }

    it("should be faster than normal string split") {
      val stringToSplit = "hello.there.you.me.him.e.haha.ehehe"
      val normalSplit = bench(stringToSplit.split("e"))
      val splitWith = bench(stringToSplit.splitWith("e"))
      splitWith should be < normalSplit
    }

  }

}
