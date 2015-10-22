package de.t7n.Ambiance.Animations

import de.t7n.Ambiance.Light.Light
import de.t7n.Ambiance.Color
/**
 * Created by thomas on 18.07.15.
 */

trait SuperSampling {
  var light: Array[Light]
  var lightGroup: Light
  var steps = 4

  def setParameters(): Unit = {
    var time = 0
    var start = 0
    var end = light.length * 4
  }

  def setColors(colors: Array[Color]): Unit = {

    colors.zipWithIndex.foreach((t) => {lightGroup.lights(t._2).color = t._1})
  }


}
