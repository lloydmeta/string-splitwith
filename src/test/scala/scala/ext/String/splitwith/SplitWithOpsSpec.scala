package scala.ext.string.splitwith

import org.scalatest.{Matchers, FunSpec}

class SplitWithOpsSpec extends FunSpec with Matchers {

  describe("#splitWith") {
    it("should split properly") {
      "".splitWith("hi") should be (Seq.empty)
      "".splitWith('.') should be (Seq.empty)
      "hi".splitWith("hi") should be (Seq(""))
      ".".splitWith('.') should be (Seq(""))
      "hello.world.yay".splitWith("ello") should be (Seq("h", ".world.yay"))
      "hello.world.yay".splitWith('.') should be(List("hello", "world", "yay"))
    }
  }

  describe("performance check") {

    def bench(f: => Unit, times: Int = 500000): Long = {
      (0 to 500000).foreach(_ => f) // warm up
      val start = System.currentTimeMillis()
      (0 to times).foreach(_ => f)
      System.currentTimeMillis() - start
    }

    it("results") {
      val stringToSplit = "hello.there.you.me.him.e.haha.ehehe"
      val normalSplit = bench(stringToSplit.split("e"))
      val splitWith = bench(stringToSplit.splitWith("e"))
      println(s"normal #split took: $normalSplit")
      println(s"splitWith took: $splitWith")
    }

  }

}
