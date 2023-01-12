import scala.io.{BufferedSource, Source}

object Day1 {

  def readInput(fuel: Int => Int): Int = {
    val bS: BufferedSource = Source.fromFile("input1.txt")
    var sum: Int = 0
    for (massStr <- bS.getLines()) {
      sum += fuel(massStr.toInt)
    }
    bS.close()
    sum
  }

  def fuel1(mass: Int): Int = {
    mass / 3 - 2
  }

  def fuel2(mass: Int): Int = {
    val fuel = fuel1(mass)
    if (fuel > 0) {
      fuel + fuel2(fuel)
    } else 0
  }

  def main(args: Array[String]) {
    println("first half: " + readInput(fuel1))
    println("second half: " + readInput(fuel2))
  }

}
