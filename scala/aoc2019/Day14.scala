import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.{BufferedSource, Source}

object Day14 {

  private val equations: HashMap[String, Equation] = {
    val bS: BufferedSource = Source.fromFile("input14.txt")
    var equats = new HashMap[String, Equation]
    for (equatStr <- bS.getLines()) {
      val mats = new ArrayBuffer[Material]
      for (matStr <- equatStr.substring(0, equatStr.indexOf('=')).split(",").map(_.trim)) {
        val space: Int = matStr.indexOf(' ')
        mats += Material(matStr.substring(space + 1), matStr.substring(0, space).toInt)
      }
      val productStr: String = equatStr.substring(equatStr.indexOf('>') + 2)
      val space: Int = productStr.indexOf(' ')
      val productName: String = productStr.substring(space + 1)
      val product: Material = Material(productName, productStr.substring(0, space).toInt)
      equats += (productName -> Equation(mats, product))
    }
    bS.close()
    equats
  }

  private var extraMaterial = new mutable.HashMap[String, Long]

  def main(args: Array[String]): Unit = {
    println(oreRequired(Material("FUEL", 4200533L)))
  }

  def oreRequired(material: Material): Long = {
    if (material.name == "ORE") {
      material.quantity
    } else {
      var oreQuanity: Long = 0L
      val equat: Equation = equations(material.name)
      if (!extraMaterial.contains(equat.product.name)) {
        extraMaterial += (equat.product.name -> 0L)
      }
      var targetQuantity: Long = material.quantity
      if (targetQuantity > extraMaterial(material.name)) {
        targetQuantity -= extraMaterial(material.name)
        extraMaterial(material.name) = 0L
        val productionQuantity: Long = equat.product.quantity
        val multiplier: Long = (targetQuantity + productionQuantity - 1) / productionQuantity
        val extraProduct: Long = (productionQuantity * multiplier) - targetQuantity
        extraMaterial(equat.product.name) += extraProduct
        for (mat <- equat.ingredients) {
          oreQuanity += oreRequired(Material(mat.name, mat.quantity * multiplier))
        }
      } else {
        extraMaterial(material.name) -= targetQuantity
      }
      oreQuanity
    }
  }

  case class Material(name: String, quantity: Long)

  case class Equation(ingredients: ArrayBuffer[Material], product: Material)

}
