import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Day7 {

  private val readInput: Array[Long] = {
    Source.fromFile("input7.txt").mkString.trim.split(",").map(_.toLong)
  }

  def buildSettings(combination: String, intList: ArrayBuffer[Array[Int]]) {
    if (combination.length == 5) {
      intList += combination.split("").map(_.toInt)
    } else {
      for (i <- 0 to 4) {
        if (combination.indexOf("" + i) == -1) {
          buildSettings(combination + i, intList)
        }
      }
    }
  }

  def main(args: Array[String]) {

    val amps = new Array[ComputeInt](5)
    for (i <- amps.indices) {
      amps(i) = new ComputeInt
    }

    var settingsList = new ArrayBuffer[Array[Int]]
    buildSettings("", settingsList)

    var maxSignal: Long = 0
    for (i <- settingsList.indices) {
      amps.foreach(_.encode(readInput))
      val nextInputs = new ArrayBuffer[Long]
      val phaseSettings: Array[Int] = settingsList(i)
      nextInputs.append(0)
      for (j <- 0 to 4) {
        nextInputs.prepend(phaseSettings(j))
        val output: Long = amps(j).exe(nextInputs.toArray)(0)
        nextInputs.clear
        nextInputs.append(output)
      }
      val signal: Long = nextInputs(0)
      if (signal > maxSignal) {
        maxSignal = signal
      }
    }

    println("first half: " + maxSignal)

    settingsList = settingsList.map(_.map(_ + 5))

    maxSignal = 0
    for (i <- settingsList.indices) {
      amps.foreach(_.encode(readInput))
      val nextInputs = new ArrayBuffer[Long]
      val phaseSettings: Array[Int] = settingsList(i)
      var firstLoop: Boolean = true
      while (!amps(0).isFinished) {
        if (firstLoop) nextInputs.append(0)
        for (j <- 0 to 4) {
          if (firstLoop) {
            nextInputs.prepend(phaseSettings(j))
          }
          val output: Long = amps(j).exe(nextInputs.toArray)(0)
          nextInputs.clear
          nextInputs.append(output)
        }
        firstLoop = false
      }
      val signal: Long = nextInputs(0)
      if (signal > maxSignal) {
        maxSignal = signal
      }
    }

    println("second half: " + maxSignal)

  }

}
