import scala.io.Source

object Day5 {

  private val readInput: Array[Long] = {
    Source.fromFile("input5.txt").mkString.trim.split(",").map(_.toLong)
  }

  def main(args: Array[String]) {
    val computeInt = new ComputeInt(readInput)
    println("first half: " + computeInt.exe(Array(1)).last)
    computeInt.encode(readInput)
    println("second half: " + computeInt.exe(Array(5))(0))
  }

}
