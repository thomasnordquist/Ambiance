package de.t7n.Ambiance.Light

import de.t7n.Ambiance.{Color, Renderable}

/**
 * Created by thomas on 16.06.15.
 */
trait Light extends Renderable {
  var lights: Array[Light]
  var lightPoints: Array[LightPoint]
  var color: Color
  def render : Array[Byte]
  def length = 0
  def toXml : scala.xml.Node
}

