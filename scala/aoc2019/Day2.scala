import scala.io.Source

object Day2 {

  private val readInput: Array[Long] = {
    Source.fromFile("input2.txt").mkString.trim.split(",").map(_.toLong)
  }

  private val prgm = new ComputeInt()

  def runPrgm(one: Int, two: Int): Long = {
    val code: Array[Long] = readInput
    code(1) = one
    code(2) = two
    prgm.encode(code)
    prgm.exe(Array())
    prgm.getCode(0)
  }

  def main(args: Array[String]) {
    println("first half: " + runPrgm(12, 2))
    for (i <- 0 to 99) {
      for (j <- 0 to 99) {
        if (runPrgm(i, j) == 19690720) {
          println("second half: " + (i * 100 + j))
          return
        }
      }
    }
  }

}
