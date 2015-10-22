package de.t7n.Ambiance.Animations

import de.t7n.Ambiance.Light.Light
import de.t7n.Ambiance._

/**
 * Created by thomas on 17.07.15.
 */
class Animation(val light: Light) {
  var time: Int = 0
  var start: Int = 0
  var end: Int = 0

  setParameters()

  def setParameters(): Unit = {
    var time = 0
    var start = 0
    var end = light.length;
  }

  def nextState(): Unit = {
    time += 1
  }
}
