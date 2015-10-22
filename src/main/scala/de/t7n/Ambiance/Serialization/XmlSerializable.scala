package de.t7n.Ambiance.Serialization

/**
 * Created by thomas on 04.08.15.
 */
trait XmlSerializable {
  def toXml : scala.xml.Node
  def fromXml(node: scala.xml.Node) : Any
  def children : Array[String]
}
