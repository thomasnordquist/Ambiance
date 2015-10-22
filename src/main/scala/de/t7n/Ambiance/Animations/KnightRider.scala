package de.t7n.Ambiance.Animations

import de.t7n.Ambiance.Light.Light
import de.t7n.Ambiance._
/**
 * Created by thomas on 17.07.15.
 */
class KnightRider(val lightGroup: Light, val baseColor : Color = new Color(1, 0, 0), var width: Int=1, var intensity: Double = 1.0) extends Animation(lightGroup){
  var direction = 1
  var steps = 1
  end = lightGroup.length
  start = width-1
  time = start

  override def nextState(): Unit = {
    time += 1 * direction
    stateForTime()
  }

  def stateForTime() = {
    if(endHasBeenReached){
      direction = -1
      time = end-width
    } else if (beginningHasBeenReached) {
      resetTime
      direction = 1
    }
    var colors: Array[Color] = turnAllLightsOff
    setRedLeds(colors)
  }

  def turnAllLightsOff = lightGroup.lights.map(x => new Color(0, 0, 0))

  def resetTime: Unit = {
    time = start
  }

  def setRedLeds(colors: Array[Color]){
    (0 to width).foreach( (x)=>{
      val multiplier = intensity * (1.0-0.15*x)
      colors(time-1*(-1)*direction + x*(-1)*direction) = new Color(
        baseColor.red * multiplier,
        baseColor.green * multiplier,
        baseColor.blue * multiplier)
    })

    setColors(colors)
  }

  def beginningHasBeenReached: Boolean = {
    time <= start
  }

  def endHasBeenReached: Boolean = {
    time+width >= end
  }

  def setColors(colors: Array[Color]): Unit = {
    colors.zipWithIndex.foreach((t) => {lightGroup.lights(t._2).color = t._1})
  }
}
