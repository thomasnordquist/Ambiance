package de.t7n.Ambiance

/**
 * Created by thomas on 16.07.15.
 */
object FrameFactory {
  def checksum(b1: Int, b2: Int, b3: Int): Byte = (b1.toByte^b2.toByte^b3.toByte).toByte
  def dataFrame(b1: Int, b2: Int, b3: Int) = new Frame(b1, b2, b3, checksum(b1, b2, b3))
  def commandFrame(b1: Int, b2: Int, b3: Int) = new Frame(b1, b2, b3, ~checksum(b1, b2, b3))
}

case class Frame(b1: Int, b2: Int, b3: Int, b4: Int) {
  def toArray = Array[Byte](b1.toByte, b2.toByte, b3.toByte, b4.toByte)
}
