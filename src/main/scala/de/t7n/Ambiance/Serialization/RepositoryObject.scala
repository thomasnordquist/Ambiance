package de.t7n.Ambiance.Serialization

/**
 * Created by thomas on 05.08.15.
 */
class RepositoryObject(
  val name: String,
  val className: String,
  val xml : scala.xml.Node,
  val singleton: Boolean = false) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[RepositoryObject]

  override def equals(other: Any): Boolean = other match {
    case that: RepositoryObject =>
      (that canEqual this) &&
        name == that.name &&
        className == that.className
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, className)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
