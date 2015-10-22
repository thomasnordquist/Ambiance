package de.t7n.Ambiance.Light

import de.t7n.Ambiance.Color
import de.t7n.Ambiance.Serialization.XmlSerializable

/**
 * Created by thomas on 17.07.15.
 */
class LightPoint(var color: Color, var offset: Int = 0) extends Light with LightPointSerializer{
  var lights = Array[Light]()
  def render = color.render
  var lightPoints = Array(this)
  override def length = 1
}

trait LightPointSerializer extends XmlSerializable {
  var color: Color

  def toXml = <LightPoint>
    {color.toXml}
  </LightPoint>

  def fromXml(node: scala.xml.Node) : LightPoint = {
    val color = Color.fromXml((node \ "Color")(0))
    new LightPoint(color)
  }

  def children = Array("Color")

}

object LightPoint extends LightPointSerializer {
  var color = Color.BLACK
}

