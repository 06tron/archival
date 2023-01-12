import scala.collection.mutable.ListBuffer
import scala.io.{BufferedSource, Source}

object Day6 {

  def readInput() {
    val bS: BufferedSource = Source.fromFile("input6.txt")
    for (orbit <- bS.getLines()) {
      Node.addNodes(orbit)
    }
    bS.close()
  }

  class Node(val name: String, var parent: Node, val moons: java.util.HashSet[Node]) {

    def neighbors(): ListBuffer[Node] = {
      val value = new ListBuffer[Node]
      if (parent != Node.noParent) {
        value += parent
      }
      val itr = moons.iterator()
      while (itr.hasNext) {
        value += itr.next
      }
      value
    }

  }

  object Node {

    val nodes = new java.util.HashMap[String, Node]

    val noParent: Node = null

    def getNode(name: String): Node = {
      if (!nodes.containsKey(name)) {
        nodes.put(name, new Node(name, noParent, new java.util.HashSet[Node]))
      }
      nodes.get(name)
    }

    def addNodes(orbit: String): Unit = {
      val names: Array[String] = orbit.split("\\)")
      val parent: Node = getNode(names(0))
      val moon: Node = getNode(names(1))
      parent.moons.add(moon)
      moon.parent = parent
    }

  }

  def numOrbits(node: Node, level: Int): Int = {
    var value: Int = level
    val moons = node.moons.iterator()
    while (moons.hasNext) {
      value += numOrbits(moons.next, level + 1)
    }
    value
  }

  def findPath(start: Node, goal: Node): ListBuffer[String] = {
    val frontier: java.util.Queue[Node] = new java.util.ArrayDeque[Node]
    frontier.add(start)

    val predecessor = new java.util.HashMap[Node, Node]
    predecessor.put(start, null)

    val path = new ListBuffer[String]

    while (!frontier.isEmpty) {
      val current: Node = frontier.remove()
      val neighbors: ListBuffer[Node] = current.neighbors()
      for (i <- neighbors.indices) {
        val next: Node = neighbors(i);
        if (!predecessor.containsKey(next)) {
          frontier.add(next)
          predecessor.put(next, current)
        }
      }
      if (current == goal) {
        path += current.name
        var previous: Node = predecessor.get(current)
        while (previous != null) {
          path.insert(0, previous.name)
          previous = predecessor.get(previous)
        }
        return path
      }
    }
    null
  }

  def main(args: Array[String]) {

    readInput()

    println("first half: " + numOrbits(Node.getNode("COM"), 0))

    println("second half: " + (findPath(Node.getNode("YOU"), Node.getNode("SAN")).length - 3))

  }

}
