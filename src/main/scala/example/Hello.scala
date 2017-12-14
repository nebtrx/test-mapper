package example


case class Source(label: String, value: Int)
case class Target(label: String, value: Int, list: List[Int])

object Mappings {
  implicit def sourceToTarget(source: Source ):Target =
    Target(source.label, source.value, List(1, 2, 3) )
}

object Hello extends App {
  import Mappings._

  val target: Target = Source("Omar", 32)


  println(target)
}
