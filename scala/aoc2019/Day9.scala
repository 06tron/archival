import scala.io.Source

object Day9 {

  private val readInput: Array[Long] = {
    Source.fromFile("input9.txt").mkString.trim.split(",").map(_.toLong)
  }

  def main(args: Array[String]): Unit = {
    val comp = new ComputeInt(readInput)
    println("first half: " + comp.exe(Array(1))(0))
    comp.encode(readInput)
    println("first half: " + comp.exe(Array(2))(0))
  }

}
