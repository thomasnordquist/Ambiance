package de.t7n.Ambiance.Sinks

import java.net.{DatagramPacket, DatagramSocket, InetSocketAddress}

/**
 * Created by thomas on 16.07.15.
 */
class UdpSink(host: String, port: Int, amount: Int) extends NetworkSink{
  val address = new InetSocketAddress(host, port)
  val clientSocket: DatagramSocket = new DatagramSocket()

  def sendPacket(data: Array[Byte]): Unit = {
    var _data = startFrames(amount) ++ data
    val packet = new DatagramPacket(_data, _data.length, address)
    clientSocket.send(packet)
  }

  def close(): Unit ={
    clientSocket.close()
  }

}
