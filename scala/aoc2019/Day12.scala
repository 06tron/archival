import scala.io.{BufferedSource, Source}

object Day12 {

  case class Moon(var px: Int, var py: Int, var pz: Int) {

    var vx: Int = 0
    var vy: Int = 0
    var vz: Int = 0

    def applyVel(): Unit = {
      px += vx
      py += vy
      pz += vz
    }

    def potEnrgy: Int = {
      px.abs + py.abs + pz.abs
    }

    def kinEnrgy: Int = {
      vx.abs + vy.abs + vz.abs
    }

  }

  private val readInput: Array[Moon] = {
    val bS: BufferedSource = Source.fromFile("input12.txt")
    val moons = new Array[Moon](4)
    for (i <- moons.indices) {
      moons(i) = Moon(0, 0, 0)
    }
    var i: Int = 0
    for (posStr <- bS.getLines()) {
      var editStr: String = posStr.substring(posStr.indexOf('=') + 1)
      moons(i).px = editStr.substring(0, editStr.indexOf(',')).toInt
      editStr = editStr.substring(editStr.indexOf('=') + 1)
      moons(i).py = editStr.substring(0, editStr.indexOf(',')).toInt
      editStr = editStr.substring(editStr.indexOf('=') + 1)
      moons(i).pz = editStr.substring(0, editStr.indexOf('>')).toInt
      i += 1
    }
    bS.close()
    moons
  }

  def main(args: Array[String]): Unit = {

    var firstState = ""
    for (i <- readInput.indices) {
      firstState += readInput(i).px
      firstState += readInput(i).pz
      firstState += readInput(i).py
      firstState += readInput(i).vx
      firstState += readInput(i).vy
      firstState += readInput(i).vz
    }

    var count: Int = 0
    while (true) {
      for (i <- readInput.indices) {
        for (j <- i + 1 until readInput.length) {
          val moon1: Moon = readInput(i)
          val moon2: Moon = readInput(j)
          if (moon1.px > moon2.px) {
            moon1.vx -= 1
            moon2.vx += 1
          }
          if (moon1.px < moon2.px) {
            moon1.vx += 1
            moon2.vx -= 1
          }
          if (moon1.py > moon2.py) {
            moon1.vy -= 1
            moon2.vy += 1
          }
          if (moon1.py < moon2.py) {
            moon1.vy += 1
            moon2.vy -= 1
          }
          if (moon1.pz > moon2.pz) {
            moon1.vz -= 1
            moon2.vz += 1
          }
          if (moon1.pz < moon2.pz) {
            moon1.vz += 1
            moon2.vz -= 1
          }
        }
      }
      readInput.foreach(_.applyVel())
      var stateStr = ""
      for (i <- readInput.indices) {
        stateStr += readInput(i).px
        stateStr += readInput(i).pz
        stateStr += readInput(i).py
        stateStr += readInput(i).vx
        stateStr += readInput(i).vy
        stateStr += readInput(i).vz
      }
      if (stateStr == firstState) {
        println(count)
        return
      } else {
        count += 1
      }
    }

    var sumEnergy: Int = 0
    for (i <- readInput.indices) {
      val pot: Int = readInput(i).potEnrgy
      print(pot + " * ")
      val kin: Int = readInput(i).kinEnrgy
      print(kin + " = ")
      println(pot * kin)
      sumEnergy += pot * kin
    }
    println(sumEnergy)
  }

}
