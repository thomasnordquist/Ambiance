package de.t7n.Ambiance.Sinks

import de.t7n.Ambiance.FrameFactory

/**
 * Created by thomas on 17.07.15.
 */
class NetworkSink {
  def startFrames(amount: Int, offset: Int = 0, universe: Int = 0): Array[Byte] = {
    val writeFrame = FrameFactory.commandFrame(0x00, 0x00, 0x10)
    val universeFrame = FrameFactory.dataFrame(0x00, 0x00, universe.toByte)
    val offsetFrame = FrameFactory.dataFrame(0x00, 0x00, offset.toByte)
    val amountFrame = FrameFactory.dataFrame(0x00, 0x00, amount.toByte)
    val a: Array[Byte] = Array[Array[Byte]](writeFrame.toArray, universeFrame.toArray, offsetFrame.toArray, amountFrame.toArray).flatten

    a
  }
}
