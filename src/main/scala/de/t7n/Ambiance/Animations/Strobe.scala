package de.t7n.Ambiance.Animations

import de.t7n.Ambiance.Light.Light
import de.t7n.Ambiance._

/**
 * Created by thomas on 25.07.15.
 */
class Strobe (val lightGroup: Light, val color: Color = new Color(1.0, 1.0, 1.0), val intensity: Double = 1.0) extends Animation(lightGroup){
  time = 1
  start = 0
  end = lightGroup.length - 1

  override def nextState(): Unit = {
    time *= -1
    val newColor = if(time > 0) color else new Color(0, 0, 0)
    val colors: Array[Color] = (start to end).map(_ => newColor).toArray
    setColors(colors)
  }

  def setColors(colors: Array[Color]): Unit = {
    colors.zipWithIndex.foreach((t) => {lightGroup.lights(t._2).color = t._1})
  }
}
