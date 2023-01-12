import scala.collection.mutable

object Day4 {

  private val input: Array[Int] = "128392-643281".split("-").map(_.toInt)

  def buildDigits(num: Int, numList: mutable.ListBuffer[Int]) {
    if (num >= 100000) {
      numList += num
    } else {
      val last: Int = if (num == 1) 0 else num % 10
      for (i <- last to 9) {
        buildDigits(num * 10 + i, numList)
      }
    }
  }

  def main(args: Array[String]) {

    val numList = new mutable.ListBuffer[Int]
    buildDigits(1, numList)

    val ansSet = new mutable.HashSet[Int]
    for (i <- numList.indices) {
      val numStr: String = numList(i).toString.substring(1)
      for (j <- numStr.indices) {
        val ans: Int = (numStr.substring(0, j + 1) + numStr.substring(j)).toInt
        if (ans >= input(0) && ans <= input(1)) {
          ansSet += ans
        }
      }
    }
    println("first half: " + ansSet.size)

    val ansList: List[Int] = ansSet.toList
    for (i <- ansList.indices) {
      var countAdj: Int = 1
      var unfit: Boolean = true
      val ansStr: String = ansList(i).toString
      for (j <- 0 until ansStr.length - 1) {
        if (ansStr.charAt(j) != ansStr.charAt(j + 1)) {
          if (countAdj == 2) {
            unfit = false
          }
          countAdj = 0
        }
        countAdj += 1
        if (countAdj == 2 && j == ansStr.length - 2) {
          unfit = false
        }
      }
      if (unfit) {
        ansSet.remove(ansList(i))
      }
    }
    println("second half: " + ansSet.size)

  }

}
