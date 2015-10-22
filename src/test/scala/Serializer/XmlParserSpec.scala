package Serializer
import de.t7n.Ambiance.Color
import de.t7n.Ambiance.Light.{Light, LightGroup, LightPoint}
import org.scalatest._

class XmlParserSpec extends FlatSpec with Matchers{
  val serializeVerbs = "serialize and deserialize"

  it should serializeVerbs + " Color" in {
    val testColor = new Color(0.1, 0.2, 0.3)
    val xml = testColor.toXml
    val newColor = Color.fromXml(xml)

    testColor should be (newColor)
  }

  it should serializeVerbs + " LightPoint" in {
    val testColor = new Color(0.1, 0.2, 0.3)
    val lightPoint = new LightPoint(testColor)
    val xml = lightPoint.toXml
    val newLightPoint = LightPoint.fromXml(xml)

    newLightPoint.color should be (testColor)
  }

  it should serializeVerbs + " LightGroup (just light points)" in {
    val lightArray : Array[Light] = (1 to 20).map(n => new LightPoint(new Color(20.0/n, 20.0/n, 20.0/n))).toArray
    val group = new LightGroup(lightArray)

    val xml = group.toXml
    val newGroup : LightGroup = LightGroup.fromXml(xml)
    newGroup.lightPoints.length should be (20)

    (0 to 19).map(n =>
      newGroup.lightPoints(n).color should be (lightArray(n).color)
    )
  }

  it should serializeVerbs + " LightGroup (sub groups)" in {
    val lightArray0 : Array[Light] = (1 to 4).map(n => new LightPoint(new Color(20.0/n, 20.0/n, 20.0/n))).toArray
    val lightArray1 : Array[Light] = (1 to 4).map(n => new LightPoint(new Color(10.0/n, 15.0/n, 5.0/n))).toArray

    val subGroup0 = new LightGroup(lightArray0)
    val subGroup1 = new LightGroup(lightArray1)

    val mainGroup = new LightGroup(Array(subGroup0, subGroup1))

    val xml = mainGroup.toXml
    val newGroup : LightGroup = LightGroup.fromXml(xml)

    newGroup.lights(0).isInstanceOf[LightGroup] should be (true)
    newGroup.lights(1).isInstanceOf[LightGroup] should be (true)

    (0 to 3).foreach(n => newGroup.lights(0).lightPoints(n).color should be (lightArray0(n).color))
    (0 to 3).foreach(n => newGroup.lights(1).lightPoints(n).color should be (lightArray1(n).color))
  }
}
