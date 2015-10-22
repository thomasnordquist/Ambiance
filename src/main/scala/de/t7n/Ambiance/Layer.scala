package de.t7n.Ambiance
import Light.Light
import LayerMixMode.LayerMixMode
import de.t7n.Ambiance.Serialization.XmlSerializable

/**
 * Created by thomas on 02.08.15.
 */
object LayerMixMode extends Enumeration {
  type LayerMixMode = Value
  val ADDITIVE = Value("ADDITIVE")
  val DESTRUCTIVE = Value("DESTRUCTIVE")
  val MULTIPLICATION = Value("MULTIPLICATION")
}

/**
 *
 * @param light
 * @param opacity
 * @param mixMode how the new color will be calculated
 * @param normalize scale all values down so the absolute peak is at 1.0
 */
class Layer (val light : Light,
             var opacity : Double = 1,
             val mixMode : LayerMixMode = LayerMixMode.ADDITIVE,
             val normalize: Boolean = false)
{
  val lights : Array[Light] = light.lights
  val lightPoints = light.lightPoints

  def normalizeColors() = {
    val max = lightPoints.par.flatMap( lightPoint => {
      Array(lightPoint.color.red, lightPoint.color.green, lightPoint.color.blue)
    }).max

    val factor = 1.0 / max
    lightPoints.foreach(lp => lp.color = lp.color * factor)
  }
}

trait LayerSerializer extends XmlSerializable {
  val light : Light
  val opacity : Double
  val mixMode : LayerMixMode
  val normalize : Boolean

  def toXml = <Layer>
    <opacity>{opacity}</opacity>
    <mixMode>{mixMode.toString}</mixMode>
    <normalize>{normalize}</normalize>
    <light>{light.toXml}</light>
  </Layer>

  def fromXml(node: scala.xml.Node) : Color = {
    val red = (node \ "red").text.toDouble
    val green = (node \ "green").text.toDouble
    val blue = (node \ "blue").text.toDouble
    new Color(red, green, blue)
  }

  def children = Array("red", "green", "blue")
}


