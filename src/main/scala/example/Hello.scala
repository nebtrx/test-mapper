package example


case class Source(label: String, value: Int)
case class Target(label: String, value: Int, list: List[Int])

object Mappings {
  implicit def sourceToTarget(source: Source ):Target =
    Target(source.label, source.value, List(1, 2, 3) )
}

object MyExtensions {

  class Optionable[T <: AnyRef](value: T) {
    def toOption: Option[T] = Option(value)
  }

  implicit def anyRefToOptionable[T <: AnyRef](value: T) = new Optionable(value)

  class NullSafeOption[A](option: Option[A] ) {
    def mapSafe[B](f: A => B): Option[B] = option match {
      case Some(null) => None
      case x          => x.map(f)
    }
  }

  implicit def nullSafeOption[A](o: Option[A] ) = new NullSafeOption(o)
}

object Hello extends App {
  import Mappings._
  import MyExtensions._

  val target: Target = Source("Omar", 32)

  println("tadaaaaaa")
  val f = Some(null)
  println(f.mapSafe((c:String) => c.substring(3, 5)).mapSafe((c:String) => c.length))

  println("".toOption)
}




