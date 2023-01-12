import scala.collection.mutable
import scala.io.Source

object Day15 {

  private val readInput: Array[Long] = {
    Source.fromFile("input15.txt").mkString.trim.split(",").map(_.toLong)
  }

  case class Location(row: Int, col: Int)

  private val droid = new ComputeInt(readInput)

  private var pastLocations = new mutable.HashMap[Location, String]

  private var maxDepth: Int = 0

  def main(args: Array[String]): Unit = {
    search(Location(0, 0))
    printLocations(-35, 35, -35, 35)
    println(maxDepth)
  }

  def next(current: Location, command: Long): Location = {
    command match {
      case 1 => Location(current.row - 1, current.col)
      case 2 => Location(current.row + 1, current.col)
      case 3 => Location(current.row, current.col - 1)
      case 4 => Location(current.row, current.col + 1)
    }
  }

  def opposite(command: Long): Long = {
    command match {
      case 1 => 2
      case 2 => 1
      case 3 => 4
      case 4 => 3
    }
  }

  def search(current: Location): Unit = {
    pastLocations += (current -> "S ")
    for (i <- 1 to 4) {
      val adjacent: Location = next(current, i)
      if (!pastLocations.contains(adjacent)) {
        search(adjacent, i, pastLocations, 1)
      }
    }
  }

  private def search(into: Location, command: Long, pastLocs: mutable.HashMap[Location, String], depth: Int): Unit = {
    droid.exe(Array(command))(0) match {
      case 2 => pastLocs += (into -> "G ")
        pastLocations = new mutable.HashMap[Location, String]
        maxDepth = 0
        search(into)
        return
      case 1 => pastLocs += (into -> ". ")
      case 0 => pastLocs += (into -> "██")
        return
    }
    if (depth > maxDepth) {
      maxDepth = depth
    }
    for (i <- 1 to 4) {
      val adjacent: Location = next(into, i)
      val pastLocsCopy = pastLocs.clone
      if (!pastLocs.contains(adjacent)) {
        search(adjacent, i, pastLocsCopy, depth + 1)
        pastLocs.addAll(pastLocsCopy)
      }
    }
    droid.exe(Array(opposite(command)))
  }

  def printLocations(minRow: Int, maxRow: Int, minCol: Int, maxCol: Int): Unit = {
    for (r <- minRow to maxRow) {
      for (c <- minCol to maxCol) {
        val current: Location = Location(r, c)
        if (pastLocations.contains(current)) {
          print(pastLocations(current))
        } else {
          print("  ")
        }
      }
      println
    }
  }

}
