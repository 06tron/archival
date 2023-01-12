import scala.collection.mutable
import scala.io.Source

object Day3 {

  private val readInput: Array[String] = {
    Source.fromFile("input3.txt").mkString.split("\\n")
  }

  case class Posit(x: Int, y: Int)

  def getPosits(inst: Array[String]): mutable.HashMap[Posit, Int] = {
    var x, y, steps: Int = 0
    val posits = new mutable.HashMap[Posit, Int]
    for (i <- inst.indices) {
      val cmd: Char = inst(i).charAt(0)

      var incr: Int = 1
      if (cmd == 'D' || cmd == 'L') incr = -1

      var vert: Boolean = true
      if (cmd == 'R' || cmd == 'L') vert = false

      for (_ <- 0 until inst(i).substring(1).toInt) {
        if (vert) {
          y += incr
        } else {
          x += incr
        }
        steps += 1
        posits.put(Posit(x, y), steps)
      }
    }
    posits
  }

  def main(args: Array[String]) {

    val posits1: mutable.HashMap[Posit, Int] = getPosits(readInput(0).split(","))
    val posits2: mutable.HashMap[Posit, Int] = getPosits(readInput(1).split(","))

    val crossings: List[Posit] = posits1.keySet.intersect(posits2.keySet).toList

    var minDist = Int.MaxValue
    for (i <- crossings.indices) {
      val dist: Int = crossings(i).x.abs + crossings(i).y.abs
      if (dist < minDist) {
        minDist = dist
      }
    }
    println("first half: " + minDist)

    var minSteps = Int.MaxValue
    for (i <- crossings.indices) {
      val steps: Int = posits1(crossings(i)) + posits2(crossings(i))
      if (steps < minSteps) {
        minSteps = steps
      }
    }
    println("second half: " + minSteps)

  }

}
