import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Day10 {

  case class Asteroid(r: Int, c: Int) {
    def distFrom(r: Int, c: Int): Int = {
      (this.r - r).abs + (this.c - c).abs
    }
  }

  private val input: Array[String] = "...###.#########.####\n.######.###.###.##...\n####.########.#####.#\n########.####.##.###.\n####..#.####.#.#.##..\n#.################.##\n..######.##.##.#####.\n#.####.#####.###.#.##\n#####.#########.#####\n#####.##..##..#.#####\n##.######....########\n.#######.#.#########.\n.#.##.#.#.#.##.###.##\n######...####.#.#.###\n###############.#.###\n#.#####.##..###.##.#.\n##..##..###.#.#######\n#..#..########.#.##..\n#.#.######.##.##...##\n.#.##.#####.#..#####.\n#.#.##########..#.##.".split("\\n")
  // private val input: Array[String] = ".#..##.###...#######\n##.############..##.\n.#.######.########.#\n.###.#######.####.#.\n#####.##.#.##.###.##\n..#####..#.#########\n####################\n#.####....###.#.#.##\n##.#################\n#####.##.###..####..\n..######..##.#######\n####.##.####...##..#\n.#####..#.######.###\n##...#.##########...\n#.##########.#######\n.####.#.###.###.#.##\n....##.##.###..#####\n.#.#.###########.###\n#.#.#.#####.####.###\n###.##.####.##.#..##".split("\\n")


  def main(args: Array[String]) {
    // 13, 11

    val i: Int = 13
    val j: Int = 11

    var leftMap = new mutable.HashMap[Double, ArrayBuffer[Asteroid]]

    for (r <- input.indices) {
      for (c <- 0 until j) {
        if (input(r).charAt(c) == '#') {
          val doub: Double = (i - r) / (j - c).toDouble
          if (!leftMap.contains(doub)) {
            leftMap += (doub -> new ArrayBuffer[Asteroid])
          }
          leftMap(doub) += Asteroid(r, c)
        }
      }
    }

    var rightMap = new mutable.HashMap[Double, ArrayBuffer[Asteroid]]

    for (r <- input.indices) {
      for (c <- j + 1 until input(r).length) {
        if (input(r).charAt(c) == '#') {
          val doub: Double = (i - r) / (j - c).toDouble
          if (!rightMap.contains(doub)) {
            rightMap += (doub -> new ArrayBuffer[Asteroid])
          }
          rightMap(doub) += Asteroid(r, c)
        }
      }
    }

    var aboveBuffer = new ArrayBuffer[Asteroid]
    var belowBuffer = new ArrayBuffer[Asteroid]

    // excluded vertical
    for (r <- input.indices) {
      if (input(r).charAt(j) == '#') {
        if (r < i) {
          aboveBuffer += Asteroid(r, j)
        }
        if (r > i) {
          belowBuffer += Asteroid(r, j)
        }
      }
    }

    aboveBuffer = aboveBuffer.sortBy(_.distFrom(i, j))
    belowBuffer = belowBuffer.sortBy(_.distFrom(i, j))

    val rightSlopes: Array[Double] = rightMap.keySet.toArray.sortWith(_ < _)
    val leftSlopes: Array[Double] = leftMap.keySet.toArray.sortWith(_ < _)

    for (k <- leftMap.keys) {
      leftMap(k) = leftMap(k).sortBy(_.distFrom(i, j))
    }
    for (k <- rightMap.keys) {
      rightMap(k) = rightMap(k).sortBy(_.distFrom(i, j))
    }

    val goal: Int = 200
    var countDestroyed: Int = 0
    var stage: Int = 0 // 0 is above, 1 is right, 2 is below, 3 is left
    var removed: Asteroid = Asteroid(i, j)
    while (countDestroyed < goal) {
      if (stage == 0) {
        if (aboveBuffer.nonEmpty) {
          removed = aboveBuffer.remove(0)
          countDestroyed += 1
          println(countDestroyed + " " + removed + " " + stage)
        }
        stage = 1
      } else if (stage == 1) {
        var c: Int = 0
        while (countDestroyed < goal && c < rightSlopes.length) {
          val buffer: ArrayBuffer[Asteroid] = rightMap(rightSlopes(c))
          if (buffer.nonEmpty) {
            removed = buffer.remove(0)
            countDestroyed += 1
            println(countDestroyed + " " + removed + " " + stage)
          }
          c += 1
        }
        stage = 2
      } else if (stage == 2) {
        if (belowBuffer.nonEmpty) {
          removed = belowBuffer.remove(0)
          countDestroyed += 1
          println(countDestroyed + " " + removed + " " + stage)
        }
        stage = 3
      } else if (stage == 3) {
        var c: Int = 0
        while (countDestroyed < goal && c < leftSlopes.length) {
          val buffer: ArrayBuffer[Asteroid] = leftMap(leftSlopes(c))
          if (buffer.nonEmpty) {
            removed = buffer.remove(0)
            countDestroyed += 1
            println(countDestroyed + " " + removed + " " + stage)
          }
          c += 1
        }
        stage = 0
      }

    }
    // println(removed)
  }


  //  def main(args: Array[String]): Unit = {
  //
  //    var maxSlopes: Int = 0
  //    var astr: Asteroid = Asteroid(0, 0)
  //
  //    for (i <- input.indices) {
  //      val row: String = input(i)
  //      for (j <- row.indices) {
  //        if (row.charAt(j) == '#') {
  //
  //          var topSlopeSet: HashSet[Double] = new HashSet[Double]
  //
  //          for (r <- input.indices) {
  //            for (c <- 0 until j) {
  //              if (input(r).charAt(c) == '#') {
  //                topSlopeSet += (i - r) / (j - c).toDouble
  //              }
  //            }
  //          }
  //
  //          var bottomSlopeSet: HashSet[Double] = new HashSet[Double]
  //
  //          for (r <- input.indices) {
  //            for (c <- j + 1 until input(r).length) {
  //              if (input(r).charAt(c) == '#') {
  //                bottomSlopeSet += (i - r) / (j - c).toDouble
  //              }
  //            }
  //          }
  //
  //          var slopes = topSlopeSet.size + bottomSlopeSet.size
  //
  //          var above, below: Boolean = false
  //
  //          for (r <- input.indices) {
  //            if (input(r).charAt(j) == '#') {
  //              if (r < i) {
  //                above = true
  //              }
  //              if (r > i) {
  //                below = true
  //              }
  //            }
  //          }
  //
  //          if (above) slopes += 1
  //          if (below) slopes += 1
  //
  //          if (slopes > maxSlopes) {
  //            maxSlopes = slopes
  //            astr = Asteroid(i, j)
  //          }
  //
  //        }
  //      }
  //
  //    }
  //    println(maxSlopes + " " + astr)
  //  }
}
