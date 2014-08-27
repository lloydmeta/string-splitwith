package scala.ext.string

object Splitting {

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
        if (from > toSplitLen - sepLen) {
          accList
        } else {
          val next = toSplit.indexOf(sep, from)
          if (next < 0) // No more until the end of the string
            toSplit.substring(from) :: accList
          else // found sep
            nextToken(next + sepLen, toSplit.substring(from, next) :: accList)
        }
      }

      val result = nextToken(0, Nil)
      result.reverse
    }
  }

}
