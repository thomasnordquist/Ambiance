package de.t7n.Ambiance

import de.t7n.Ambiance.Serialization.XmlSerializable

/**
 * Created by thomas on 16.06.15.
 */

/**
 * @param red values from 0-1
 * @param green values from 0-1
 * @param blue values from 0-1
 */
class Color(var red: Double = 0, var green: Double = 0, var blue: Double = 0) extends ColorSerializer{
  def render = {
    validateColors()

    val conversionFactor = 255
    val r = (red * conversionFactor).toInt
    val g = (green * conversionFactor).toInt
    val b = (blue * conversionFactor).toInt

    val checksum = r ^ g ^ b
    Array(r.toByte,
      g.toByte,
      b.toByte,
      checksum.toByte)
  }

  def * (factor : Double): Color = new Color(red * factor, green * factor, blue * factor)
  def * (color : Color): Color = new Color(red * color.red, green * color.green, blue * color.blue)
  def + (color : Color): Color = new Color(red + color.red, green + color.green, blue + color.blue)


  def validateColors() = {
    red = validColorValue(red)
    green = validColorValue(green)
    blue = validColorValue(blue)
  }

  /**
   * To ensure that values do not exceed their values due to
   * @param value
   * @param lower
   * @param upper
   * @return
   */
  def validColorValue(value : Double, lower: Double = 0.0, upper: Double = 1.0): Double = {
    if(value > upper)
      upper
    else if(value < lower)
      lower
    else
      value
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Color]

  override def equals(other: Any): Boolean = other match {
    case that: Color =>
      (that canEqual this) &&
        red == that.red &&
        green == that.green &&
        blue == that.blue
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(red, green, blue)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"Color($red, $green, $blue)"
}

trait ColorSerializer extends XmlSerializable {
  var red:Double
  var green : Double
  var blue : Double

  def toXml = <Color>
    <red>{red}</red>
    <green>{green}</green>
    <blue>{blue}</blue>
  </Color>

  def fromXml(node: scala.xml.Node) : Color = {
    val red = (node \ "red").text.toDouble
    val green = (node \ "green").text.toDouble
    val blue = (node \ "blue").text.toDouble
    new Color(red, green, blue)
  }

  def children = Array("red", "green", "blue")
}

object Color extends Color(0,0,0) {
  val BLACK  = new Color(0,0,0)
  val WHITE  = new Color(1,1,1)
  val RED  = new Color(1,0,0)
  val GREEN  = new Color(0,1,0)
  val BLUE  = new Color(0,0,1)
}