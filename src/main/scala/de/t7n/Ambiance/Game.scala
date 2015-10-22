package de.t7n.Ambiance
import Animations._
import de.t7n.Ambiance.Light.{LightPoint, LightGroup, Light}
import de.t7n.Ambiance.Sinks.{UdpSink, TcpSink}

/**
 * Created by thomas on 10.07.15.
 */
class Game(val fps : Int = 13) {
  FrameTime.setFps(fps)

  var leds = 60
  var l1 : Array[Light] = (0 to (leds/4)-1).map(_ => new LightPoint(new Color(0,0,0), 0)).toArray
  var left : Array[Light] = (0 to (leds/2)-1).map(_ => new LightPoint(new Color(0,1,1), 0)).toArray
  var right : Array[Light] = (0 to (leds/2)-1).map(_ => new LightPoint(new Color(1,0,1), 0)).toArray

  var l2 : Array[Light] = l1.clone

  var rainbowLeds : Array[Light] = (0 to (leds)-1).map(_ => new LightPoint(new Color(0,0,0), 0)).toArray

  var strip1 = new LightGroup(l1)
  var strip2 = new LightGroup(l2)
  strip2.reverse()

  val kickerBasicLayer = new Layer(new LightGroup(left ++ right))
  val knightRiderLight = new LightGroup(Array(strip1, strip2, strip1, strip2))
  val knightRiderLayer = new Layer(knightRiderLight, normalize = true, mixMode = LayerMixMode.MULTIPLICATION)
  var rainbowLight = new LightGroup(rainbowLeds)
  val rainbowLayer = new Layer(rainbowLight, opacity = 1)

  val lights = rainbowLight

  var running = true

  var layers : Array[Layer] = Array(kickerBasicLayer, knightRiderLayer)
  val emulatorSink = new UdpSink("localhost", 1234, leds)
  val kickerSink = new UdpSink("192.168.178.3", 1234, leds)

  var knightRider = new KnightRider(strip1, new Color(1, 1, 1), 3, intensity = 1.0)
  var rainbowAnimation = new HsvAnimation(rainbowLight, intensity = 1)
  var strobeAnimation = new Strobe(rainbowLight)

  def gameLoop() {
    while (running) {
      FrameTime.start()
      //new Color(1000,1000,1000)
      knightRider.nextState()
      rainbowAnimation.nextState()
      //strobeAnimation.nextState()
      val state = renderedState

      kickerSink.sendPacket(state)
      emulatorSink.sendPacket(state)

      FrameTime.stop()
      FrameTime.sleepTheRestOff()
    }

    emulatorSink.close()
    kickerSink.close()
  }

  def renderedState = {
    val target = LayerMixer.mix(layers)
    target.light.lightPoints.par.flatMap(_.render).toArray
  }
}
