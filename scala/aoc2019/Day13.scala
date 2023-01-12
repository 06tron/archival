import scala.io.Source

object Day13 {

  private val readInput: Array[Long] = {
    Source.fromFile("input13.txt").mkString.trim.split(",").map(_.toLong)
  }

  private val screen: Array[Array[Int]] = {
    val init = new Array[Array[Int]](25)
    for (i <- init.indices) {
      init(i) = new Array[Int](40)
    }
    init
  }

  //  def main(args: Array[String]): Unit = {
  //    val comp = new ComputeInt(readInput)
  //    comp.set(0, 2)
  //    var score: Long = 0
  //    var ballX: Int = 0
  //    var paddleX: Int = 0
  //    var joyStick: Int = 0 // 0 = neutral, 1 = left, -1 = right
  //    while (!comp.isFinished) {
  //      joyStick = 0
  //      if (paddleX > ballX) {
  //        joyStick = -1
  //      } else if (paddleX < ballX) {
  //        joyStick = 1
  //      }
  //      val outBuffer: ArrayBuffer[Long] = comp.exe(Array(joyStick))
  //      var i: Int = 0
  //      while (i < outBuffer.length) {
  //        val param1: Int = outBuffer(i).toInt
  //        val param2: Int = outBuffer(i + 1).toInt
  //        if (param1 == -1 && param2 == 0) {
  //          score = outBuffer(i + 2)
  //          println(score)
  //        } else {
  //          val tileID: Int = outBuffer(i + 2).toInt
  //          if (tileID == 4) {
  //            ballX = param1
  //          }
  //          if (tileID == 3) {
  //            paddleX = param1
  //          }
  //          screen(param2)(param1) = outBuffer(i + 2).toInt
  //        }
  //        i += 3
  //      }
  //      //showDisplay()
  //    }
  //  }

  def showDisplay(): Unit = {
    for (y <- screen.indices) {
      for (x <- screen(y).indices) {
        val tileID: Int = screen(y)(x)
        if (tileID == 0) {
          print(' ')
        } else if (tileID == 1) {
          print('|')
        } else if (tileID == 2) {
          print('#')
        } else if (tileID == 3) {
          print('–')
        } else if (tileID == 4) {
          print('O')
        } else {
          print('?')
        }
      }
      println
    }
    println
  }

}
