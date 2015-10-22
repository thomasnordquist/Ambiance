package de.t7n.Ambiance.Animations

import de.t7n.Ambiance.Light.Light
import de.t7n.Ambiance._

/**
 * Created by thomas on 25.07.15.
 */
class HsvAnimation (val lightGroup: Light, var intensity: Double = 1.0) extends Animation(lightGroup){
  var totalDegrees = 360
  var direction = 1
  var steps = 1
  end = lightGroup.length - 1
  start = 0
  time = start

  override def nextState(): Unit = {
    time = (time + 1) % lightGroup.length
    stateForTime()
  }

  def stateForTime() = {
    val length = end - start
    val step : Double = 1.0 * totalDegrees / length

    var i = time

    val colors: Array[Color] = (start to end).map( x =>
      {
        val color = colorForStep(step, i)
        i = (i + 1) % end
        color
      }
    ).toArray

    setColors(colors)
  }

  def colorForStep(step: Double, i: Int): Color = {
    var rgbColorTriplet = RgbHsv.hsvToRgb(step * i, 1, intensity)
    new Color(rgbColorTriplet._1, rgbColorTriplet._2, rgbColorTriplet._3)
  }

  def turnAllLightsOff = lightGroup.lights.map(x => new Color(0, 0, 0))

  def resetTime: Unit = {
    time = start
  }

  def setColors(colors: Array[Color]): Unit = {
    colors.zipWithIndex.foreach((t) => {lightGroup.lights(t._2).color = t._1})
  }
}
