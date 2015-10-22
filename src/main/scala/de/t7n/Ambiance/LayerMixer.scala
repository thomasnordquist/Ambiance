package de.t7n.Ambiance

import de.t7n.Ambiance.LayerMixMode.LayerMixMode
import de.t7n.Ambiance.Light.{LightGroup, LightPoint, Light}

/**
 * Created by thomas on 03.08.15.
 */
class LayerMixer {
  def mix(layers: Array[Layer]): Layer = {
    val targetSize = layers.maxBy(_.light.length).light.length

    val targetLayer: Layer = new Layer(
      new LightGroup(
        (1 to targetSize).map(_ => new LightPoint(new Color(0, 0, 0))).toArray
      )
    )

    layers.foreach(layer => {
      var i = 0
      layer.lightPoints.foreach(light => {
        val targetLight = targetLayer.light.lights(i)
        targetLight.color = ColorMixer.mixColors(targetLight.color, light.color, layer.opacity, layer.mixMode)
        i += 1
      })
      if(layer.normalize) {
        targetLayer.normalizeColors()
      }
    })

    targetLayer
  }
}

class ColorMixer {
  def mixColors(target: Color, source: Color, opacity : Double, mixMode: LayerMixMode): Color = {

    if(LayerMixMode.ADDITIVE == mixMode)
      return additiveColorMix(target, source, opacity)
    else if(LayerMixMode.DESTRUCTIVE == mixMode) {
      return destructiveColorMix(target, source, opacity)
    } else if(LayerMixMode.MULTIPLICATION == mixMode) {
      return multiplicationColorMix(target, source, opacity)
    }

    throw new LayerMixModeNotImplementedException
  }

  def destructiveColorMix(target: Color, source: Color, opacity: Double): Color = {
    val invertedOpacity = 1 - opacity
    return (target * invertedOpacity) + (source * opacity)

  }

  def additiveColorMix(target: Color, source: Color, opacity: Double): Color = {
    return target + (source * opacity)
    return new Color(
      target.red + source.red * opacity,
      target.green + source.green * opacity,
      target.blue + source.blue * opacity
    )
  }

  def multiplicationColorMix(target: Color, source: Color, opacity: Double): Color = {
    return target * (source * opacity)
    return new Color(
      target.red * source.red * opacity,
      target.green * source.green * opacity,
      target.blue * source.blue * opacity
    )
  }
}

object ColorMixer extends ColorMixer
object LayerMixer extends LayerMixer

class LayerMixModeNotImplementedException extends Exception
