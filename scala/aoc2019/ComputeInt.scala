import ComputeInt.{maxInstructionLength, md, op}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

class ComputeInt {

  private var code: Array[Long] = _
  private var ptr: Int = 0
  private var finished: Boolean = false
  private var relativeBase: Int = 0

  def this(code: Array[Long]) {
    this()
    encode(code)
  }

  def encode(code: Array[Long]): Unit = {
    this.code = new Array[Long](code.length * 2)
    for (i <- code.indices) {
      this.code(i) = code(i)
    }
    ptr = 0
    finished = false
    relativeBase = 0
  }

  def set(index: Int, value: Long): Unit = {
    code(index) = value
  }

  def getCode: Array[Long] = code

  def isFinished: Boolean = finished

  def exe(args: Array[Long]): ArrayBuffer[Long] = {

    val output = new ArrayBuffer[Long]

    var inputCount = 0

    while (ptr < code.length) {

      val modes = new Array[Int](maxInstructionLength)
      for (i <- 1 until modes.length) {
        modes(i) = (code(ptr).toInt / math.pow(10, i + 1).toInt) % 10
      }

      val opcode: Int = code(ptr).toInt % 100

      if (opcode == op("HALT")) {
        ptr = 0
        finished = true
        relativeBase = 0
        return output
      } else if (opcode == op("ADD")) {
        code(position(3)) = code(position(1)) + code(position(2))
        ptr += 4
      } else if (opcode == op("MULT")) {
        code(position(3)) = code(position(1)) * code(position(2))
        ptr += 4
      } else if (opcode == op("INPUT")) {
        if (inputCount == args.length) return output
        code(position(1)) = args(inputCount)
        inputCount += 1
        ptr += 2
      } else if (opcode == op("OUTPUT")) {
        output += code(position(1))
        ptr += 2
      } else if (opcode == op("JUMP_T")) {
        if (code(position(1)) != 0) {
          ptr = code(position(2)).toInt
        } else ptr += 3
      } else if (opcode == op("JUMP_F")) {
        if (code(position(1)) == 0) {
          ptr = code(position(2)).toInt
        } else ptr += 3
      } else if (opcode == op("LESS")) {
        code(position(3)) = {
          if (code(position(1)) < code(position(2))) 1 else 0
        }
        ptr += 4
      } else if (opcode == op("EQUAL")) {
        code(position(3)) = {
          if (code(position(1)) == code(position(2))) 1 else 0
        }
        ptr += 4
      } else if (opcode == op("ADJUST")) {
        relativeBase += code(position(1)).toInt
        ptr += 2
      } else {
        println("something went wrong")
        ptr = 0
        relativeBase = 0
        return output
      }

      def position(i: Int): Int = {
        if (modes(i) == md("POSITION")) {
          code(ptr + i).toInt
        } else if (modes(i) == md("IMMEDIATE")) {
          ptr + i
        } else if (modes(i) == md("RELATIVE")) {
          code(ptr + i).toInt + relativeBase
        } else {
          println("something went wrong")
          -1
        }
      }

    }
    output
  }

}

object ComputeInt {

  val maxInstructionLength: Int = 4

  var op = new HashMap[String, Int]
  op += ("ADD" -> 1)
  op += ("MULT" -> 2)
  op += ("INPUT" -> 3)
  op += ("OUTPUT" -> 4)
  op += ("JUMP_T" -> 5)
  op += ("JUMP_F" -> 6)
  op += ("LESS" -> 7)
  op += ("EQUAL" -> 8)
  op += ("ADJUST" -> 9)
  op += ("HALT" -> 99)

  var md = new HashMap[String, Int]
  md += ("POSITION" -> 0)
  md += ("IMMEDIATE" -> 1)
  md += ("RELATIVE" -> 2)

}
