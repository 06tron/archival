import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Day16 {

  class Pattern(forIndex: Int) {

    val repeatSeq: ArrayBuffer[Byte] = {
      val buffer = new ArrayBuffer[Byte]()
      for (_ <- 0 to forIndex) {
        buffer += 0
      }
      for (_ <- 0 to forIndex) {
        buffer += 1
      }
      for (_ <- 0 to forIndex) {
        buffer += 0
      }
      for (_ <- 0 to forIndex) {
        buffer += -1
      }
      buffer += buffer.remove(0)
    }

    def get(atIndex: Int): Byte = {
      repeatSeq(atIndex % repeatSeq.length)
    }

  }

  private val readInput: Array[Byte] = {
    Source.fromFile("input16.txt").mkString.trim.split("").map(_.toByte)
  }

  def main(args: Array[String]): Unit = {
    val offset: Int = Source.fromFile("input16.txt").mkString.substring(0, 7).toInt
    println(multiphase(readInput, 100).mkString(","))
  }

  def multiphase(input: Array[Byte], quantity: Int): Array[Byte] = {
    var arr = input
    for (_ <- 0 until quantity) {
      arr = phase(arr)
    }
    arr
  }

  def phase(input: Array[Byte]): Array[Byte] = {
    val output = new Array[Byte](input.length)
    for (i <- input.indices) {
      val patt = new Pattern(i)
      var sum: Int = 0
      for (j <- input.indices) {
        sum += input(j) * patt.get(j)
      }
      output(i) = (sum.abs % 10).toByte
    }
    output
  }


}
