package example

object Hello extends App {
  case class Source(label: String, value: Int)
  case class Target(label: String, value: Int)
  case class Another(label: String, value: Int)

  import io.bfil.automapper._

  val source = Source("label", 10)

  trait MyMappings {
    implicit val mapping1 = generateMapping[Source, Target]
    implicit val mapping2 = generateMapping[Source, Another]
  }

  object ExampleMappings extends MyMappings {
    val target1 = automap(source).to[Target]
    val target2 = automap(source).to[Another]
    // Error Target -> Another not defined
    val target3 = automap(target1).to[Another]
  }

  println(ExampleMappings.target1)
  println(ExampleMappings.target2)
  println(ExampleMappings.target3)
}
