package scala.ext.string

package object splitwith {

  implicit class SplitWithOps(val s: String) extends AnyVal {
    /**
     * Splits the current [[String]] into a list of [[String]] using a [[Char]]
     */
    def splitWith(splitter: Char): List[String] = splitQuick(s, s"$splitter")

    /**
     * Splits the current [[String]] into a list of [[String]] using a [[String]]
     */
    def splitWith(splitter: String): List[String] = splitQuick(s, splitter)
  }

  def splitQuick(toSplit: String, sep: String): List[String] = {
    if (toSplit.isEmpty) Nil
    else {
      val sepLen = sep.length
      val toSplitLen = toSplit.length

      @scala.annotation.tailrec
      def nextToken(from: Int, accList: List[String]): List[String] = {
        val nextIndexOfToken = toSplit.indexOf(sep, from)
        if (nextIndexOfToken < 0) { // separator not in string portion
          val remainder = toSplit.substring(from)
          if (remainder.isEmpty)
            accList
          else
            remainder :: accList
        }
        else // found sep
          nextToken(nextIndexOfToken + sepLen, toSplit.substring(from, nextIndexOfToken) :: accList)
      }

      val result = nextToken(0, Nil)
      result.reverse
    }
  }

}
