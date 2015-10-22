import de.t7n.Ambiance.Color
import de.t7n.Ambiance.Light.{Light, LightGroup, LightPoint}
import org.scalatest._

class LightGroupSpec extends FlatSpec with Matchers {
  val lightsOff : Array[Light] = (0 to 3).map(_ => {new LightPoint(Color.BLACK)}).toArray
  val lightsOn : Array[Light] = (0 to 3).map(_ => {new LightPoint(Color.WHITE)}).toArray

  val subGroup1 = new LightGroup(lightsOn)
  val subGroup2 = new LightGroup(lightsOff)
  val mainGroup = new LightGroup(Array(subGroup1, subGroup2))

  val aggregateTestCase = "Aggregation of LightPoints of child LightGroups to a lightPoints array"

  aggregateTestCase should "have same amount of children" in {
    val lengthOfBothGroups = subGroup1.lights.length + subGroup2.lights.length
    mainGroup.length should be (lengthOfBothGroups)
  }

  it should "be in the right order" in {
    (0 to 3).foreach(
      mainGroup.lightPoints(_).color should be (Color.WHITE)
    )

    (4 to 7).foreach(
      mainGroup.lightPoints(_).color should be (Color.BLACK)
    )
  }
}