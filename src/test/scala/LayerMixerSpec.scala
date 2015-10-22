import de.t7n.Ambiance.Light.{Light, LightGroup, LightPoint}
import de.t7n.Ambiance.{LayerMixMode, Color, Layer, LayerMixer}
import org.scalatest._

import scala.collection.mutable.Stack

class LayerMixerSpec extends FlatSpec with Matchers {

  lazy val allOut : Array[Light] = (0 to 4).map(_ => new LightPoint(new Color(0, 0, 0))).toArray
  lazy val allRed : Array[Light] = (1 to 4).map(_ => new LightPoint(new Color(1, 0, 0))).toArray
  lazy val allGreen : Array[Light] = (1 to 4).map(_ => new LightPoint(new Color(0, 1, 0))).toArray
  lazy val fullOn : Array[Light] = (1 to 4).map(_ => new LightPoint(new Color(1, 1, 1))).toArray

  val fullOn10 : Array[Light] = (1 to 4).map(_ => new LightPoint(new Color(10, 10, 10))).toArray

  lazy val blueishColor : Array[Light] = (1 to 4).map(_ => new LightPoint(new Color(0.2, 0.4, 0.8))).toArray

  lazy val layerOff = new Layer(new LightGroup(allOut))
  lazy val blueishLayer = new Layer(new LightGroup(blueishColor))

  lazy val layerOnAdditiveHalfOpacity = new Layer(new LightGroup(fullOn), opacity = 0.5, mixMode = LayerMixMode.ADDITIVE)
  lazy val layerOnAdditiveFullOpacity = new Layer(new LightGroup(fullOn), opacity = 1, mixMode = LayerMixMode.ADDITIVE)

  lazy val additiveLayerGreen = new Layer(new LightGroup(allGreen), opacity = 1, mixMode = LayerMixMode.ADDITIVE)
  lazy val additiveLayerRed = new Layer(new LightGroup(allRed), opacity = 1, mixMode = LayerMixMode.ADDITIVE)

  lazy val destructiveLayerRedFullOpacity = new Layer(new LightGroup(allRed), opacity = 1, mixMode = LayerMixMode.DESTRUCTIVE)
  lazy val destructiveLayerRedHalfOpacity = new Layer(new LightGroup(allRed), opacity = 0.5, mixMode = LayerMixMode.DESTRUCTIVE)

  lazy val multiplicationLayer10 = new Layer(new LightGroup(fullOn10), opacity = 1, mixMode = LayerMixMode.MULTIPLICATION)
  val multiplicationLayer10WithNormalization = new Layer(new LightGroup(fullOn10), opacity = 1, mixMode = LayerMixMode.MULTIPLICATION, normalize = true)

  it should "mix color layers in additive mode" in {
    val targetLayer : Layer = LayerMixer.mix(Array(layerOff, layerOnAdditiveHalfOpacity))

    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(0.5, 0.5, 0.5))
    )

    /* fullOn has 1 element less then allOut, this is why the last color should not be affected and therefor be off */
    targetLayer.lights(4).color should be (Color.BLACK)
  }


  it should "mix color layers in additive mode (different colors)" in {
    val targetLayer : Layer = LayerMixer.mix(Array(additiveLayerGreen, additiveLayerRed))
    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(1, 1, 0))
    )
  }

  it should "mix color layers in destructive mode with full opacity" in {
    val targetLayer : Layer = LayerMixer.mix(Array(additiveLayerGreen, destructiveLayerRedFullOpacity))
    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(1, 0, 0))
    )
  }

  it should "mix color layers in destructive mode with half opacity" in {
    val targetLayer : Layer = LayerMixer.mix(Array(additiveLayerGreen, destructiveLayerRedHalfOpacity))
    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(0.5, 0.5, 0))
    )
  }

  it should "mix color layers in multiplication mode with full opacity" in {
    val targetLayer : Layer = LayerMixer.mix(Array(layerOnAdditiveFullOpacity, multiplicationLayer10))
    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(10, 10, 10))
    )
  }

  it should "mix color layers with normalization" in {
    val targetLayer : Layer = LayerMixer.mix(Array(blueishLayer, multiplicationLayer10WithNormalization))
    /* testing from 0 to 3 if colors are correct */
    (0 to 3).foreach(
      targetLayer.lights(_).color should be (new Color(0.25, 0.5, 1))
    )
  }

}