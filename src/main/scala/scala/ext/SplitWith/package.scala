package scala.ext

package object SplitWith {

  implicit class SplitWithOps(val s: String) extends AnyVal {
    /**
     * Splits the current [[String]] into a list of [[String]] using a [[Char]]
     */
    def splitWith(splitter: Char): List[String] = splitWithChar(s, splitter)

    /**
     * Splits the current [[String]] into a list of [[String]] using a [[String]]
     */
    def splitWith(splitter: String): List[String] = splitWithString(s, splitter)
  }

  def splitWithChar(toSplit: String, splitWith: Char): List[String] = {
    val (list, finalBuilder) = toSplit.foldLeft((Nil: List[String], new StringBuilder)){ (acc, elem) =>
      val (seq, builder) = acc
      if (elem == splitWith) {
        (builder.mkString :: seq, new StringBuilder)
      } else {
        (seq, builder.append(elem))
      }
    }
    (if (finalBuilder.isEmpty)
      list
    else
      finalBuilder.mkString :: list).reverse
  }

  def splitWithString(toSplit: String, splitWith: String): List[String] = {
    if (splitWith.length == 1)
      splitWithChar(toSplit, splitWith.head)
    else {
      val splitterLength = splitWith.length
      @annotation.tailrec
      def step(remaining: String, seq: List[String], builder: StringBuilder): List[String] = {
        if (remaining.isEmpty)
          if (builder.isEmpty)
            seq
          else
            builder.mkString :: seq
        else {
          val beginning = remaining.take(splitterLength)
          if (beginning == splitWith)
            step(remaining.drop(splitterLength), builder.mkString :: seq, new StringBuilder)
          else
            step(remaining.tail, seq, builder.append(beginning.head))
        }
      }
      step(toSplit, Nil, new StringBuilder).reverse
    }
  }

}
