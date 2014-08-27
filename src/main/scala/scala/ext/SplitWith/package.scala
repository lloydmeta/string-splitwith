package scala.ext

package object SplitWith {

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

  def splitQuick(what: String, sep: String): List[String] = {
    if (what.isEmpty) Nil
    else {
      val sepLen = sep.length
      val whatLen = what.length

      @annotation.tailrec
      def nextToken(from: Int, currentList: List[String]): List[String] = {
        if (from > whatLen - sepLen) {
          currentList
        } else {
          val next = what.indexOf(sep, from)
          if (next < 0) {
            // not found : last token
            what.substring(from) :: currentList
          } else {
            // found sep
            nextToken(next + sepLen, what.substring(from, next) :: currentList)
          }
        }
      }

      val result = nextToken(0, Nil)
      result.reverse
    }
  }

}
