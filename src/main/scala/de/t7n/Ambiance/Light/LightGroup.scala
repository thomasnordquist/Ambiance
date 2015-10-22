package de.t7n.Ambiance.Light

import de.t7n.Ambiance.Color
import de.t7n.Ambiance.Serialization.{InvalidArgumentException, XmlSerializable}

/**
 * Created by thomas on 17.07.15.
 */
class LightGroup(var lights: Array[Light]) extends Light with LightGroupSerializer{
  var direction = 1
  var color : Color = null

  var lightPoints : Array[LightPoint] = updatedLightPoints
  override def length = lightPoints.length

  /**
   * 
   * @return
   */
  def updatedLightPoints : Array[LightPoint] = lights.flatMap(light => {
    if(light.isInstanceOf[LightPoint])
      Array(light.asInstanceOf[LightPoint])
    else
      light.lightPoints
  })
  
  def reverse(): Unit = {
    lights = lights.reverse
    lightPoints = updatedLightPoints
  }

  def render = {
    lights.flatMap(_.render)
  }
}

trait LightGroupSerializer extends XmlSerializable {
  var lights: Array[Light]

  def toXml = <LightGroup>
    {lights.map(
      _.toXml
    )}
  </LightGroup>

  def fromXml(node: scala.xml.Node) : LightGroup = {
    var lights : Array[Light] = (node \ "LightGroup").map(lightNode => {
          LightGroup.fromXml(lightNode)
        }).toArray

    if(lights.length == 0) {
      lights = (node \ "LightPoint").map(lightNode => {
        LightPoint.fromXml(lightNode)
      }).toArray
    }

    new LightGroup(lights)
  }

  def children = Array("LightGroup", "LightPoint")

}

object LightGroup extends LightGroupSerializer {
  var lights : Array[Light] = Array()
}
