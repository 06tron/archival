import scala.collection.mutable.ArrayBuffer

object Chess {

  def main(args: Array[String]) {
    val rec: ArrayBuffer[String] = ArrayBuffer.fill(16)("_")
    val num: Array[Int] = Array(0, 2, 4, 8)
    for (j <- num.indices) {
      var pla: String = "" // H O X S
      for (i <- 0 until 4) {
        val pow: Int = Math.pow(2, i).toInt
        val ind: Int = if ((num(j) / pow) % 2 == 1) num(j) - pow else num(j) + pow
        val str: String = rec(ind)
        if (str == "_") {
          rec(ind) = "*"
        } else {
          pla += str
        }
      }
      while (pla.length < 4) {
        var nex: String = ""
        if (!pla.contains("H")) {
          nex = "H"
        } else if (!pla.contains("O")) {
          nex = "O"
        } else if (!pla.contains("X")) {
          nex = "X"
        } else {
          nex = "S"
        }
        rec(rec.indexOf("*")) = nex
        pla += nex
      }
      println(rec.toString.substring(11) + " " + pla)
    }
  }

}
