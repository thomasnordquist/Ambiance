package de.t7n.Ambiance.Sinks

import java.io._
import java.net._

/**
 * Created by thomas on 17.07.15.
 */
class TcpSink(host: String, port: Int, amount: Int) extends NetworkSink{
  val socket = new Socket(host, port)

  val out = new DataOutputStream(socket.getOutputStream())

  def sendPacket(data: Array[Byte]): Unit = {
    val _data = startFrames(amount) ++ data
    out.write(_data, 0, _data.length)
  }

  def close(): Unit ={
    socket.close()
  }
}