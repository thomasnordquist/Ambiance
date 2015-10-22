package de.t7n.Ambiance

/**
 * Created by thomas on 16.07.15.
 */
object FrameTime {
  var startTime = System.currentTimeMillis
  var stopTime = System.currentTimeMillis
  var frameTime = 0
  var last :Long = 0

  def current = startTime

  def setFps(fps: Int) = frameTime = 1000 / fps

  def start() = {
    last = stopTime
    startTime = System.currentTimeMillis
  }

  def stop() = stopTime = System.currentTimeMillis

  def sleepTheRestOff(): Unit = {
    val diff = remainingTime
    if(diff>0)
      Thread.sleep(diff)
  }

  protected def remainingTime: Long = {
    frameTime - (stopTime - startTime)
  }
}
