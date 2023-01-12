object Day22 {

  private val input: Array[String] = "deal with increment 73\ncut -6744\ndeal into new stack\ncut 9675\ndeal with increment 63\ncut -8047\ndeal with increment 21\ncut -4726\ndeal with increment 39\ncut -537\ndeal with increment 39\ncut -6921\ndeal with increment 15\ncut 2673\ndeal into new stack\ncut 2483\ndeal with increment 66\ndeal into new stack\ncut 1028\ndeal with increment 48\ndeal into new stack\ncut -418\ndeal with increment 15\ncut 9192\ndeal with increment 62\ndeal into new stack\ndeal with increment 23\ncut 2840\ndeal with increment 50\ncut 6222\ndeal with increment 20\ndeal into new stack\ncut -6855\ndeal with increment 50\ncut -1745\ndeal with increment 27\ncut 4488\ndeal with increment 71\ndeal into new stack\ndeal with increment 28\ncut -2707\ndeal with increment 40\ndeal into new stack\ndeal with increment 32\ncut 8171\ndeal with increment 74\ndeal into new stack\ncut -2070\ndeal with increment 61\ndeal into new stack\ndeal with increment 46\ncut 4698\ndeal with increment 23\ncut -3480\ndeal with increment 30\ncut -6662\ndeal with increment 53\ncut -5283\ndeal with increment 43\ndeal into new stack\ncut -1319\ndeal with increment 9\ncut -8990\ndeal into new stack\ndeal with increment 9\ndeal into new stack\ncut -5058\ndeal with increment 28\ncut -7975\ndeal with increment 57\ncut 2766\ndeal with increment 19\ncut 8579\ndeal into new stack\ndeal with increment 22\ndeal into new stack\ncut 9835\ndeal with increment 36\ncut -2485\ndeal with increment 52\ncut -5818\ndeal with increment 9\ncut 5946\ndeal with increment 51\ndeal into new stack\ncut -5600\ndeal with increment 75\ncut -9885\ndeal with increment 27\ncut -2942\ndeal with increment 68\ncut 3874\ndeal with increment 36\ndeal into new stack\ndeal with increment 20\ncut -2565\ndeal with increment 17\ncut -9109\ndeal with increment 62\ncut 2175".split("\\n")

  def main(args: Array[String]): Unit = {
    val deck1 = new Deck(119315717514047L)
    println("deck1 " + 0 + ": " + deck1.start + " " + deck1.incr)
    for (c <- 1 to 2) {
      for (i <- input.indices) {
        val str: String = input(i)
        str.charAt(0) match {
          case 'c' => deck1.cut(str.substring(str.indexOf(' ') + 1).toInt)
          case 'd' =>
            if (str == "deal into new stack") {
              deck1.redeal()
            } else {
              deck1.shuffle(str.substring(str.lastIndexOf(' ') + 1).toInt)
            }
        }
      }
      println("deck1 " + c + ": " + deck1.start + " " + deck1.incr)
    }
  }

  class Deck(val size: Long) {

    var start: Long = 0
    var incr: Long = 1

    def shuffle(n: Long): Unit = {
      incr = (incr * n) % size
      start = (start * n) % size
    }

    def redeal(): Unit = {
      incr *= -1
      start = (size - 1) - start
    }

    def cut(n: Long): Unit = {
      if (n < 0) {
        start = (start + n.abs) % size
      } else {
        if (n > start) {
          start += size - n
        } else {
          start -= n
        }
      }
    }

    def cardAt(index: Long): Long = {
      var i: Long = start
      var card: Long = 0L
      while (card < size) {
        if (i == index) return card
        if (incr > 0) {
          i = (i + incr) % size
        } else {
          if (incr.abs > i) {
            i += incr + size
          } else {
            i += incr
          }
        }
        card += 1
      }
      -1
    }

  }

}
