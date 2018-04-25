package example

import java.util

import MappingConfig._
import io.bfil.automapper._

import collection.JavaConverters._



case class Source(label: String, value: Int)
case class Target(label: String, value: Int, list: List[Int])


case class Quote(name: String, amount: Int, items: util.List[String], source: Source)
case class QuoteDTO(name: String, amount: Int, list: List[String], source: Source)

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

object MappingConfig {
  import example.Mapper.Mapping
  implicit object S2T extends Mapping[Source, Target]((s) => Target(s.label, s.value, List.empty))
}

object Mapper {
  class Mapping[S, T](val mapFn: S => T)

  final class MapType[S](source:S) {
    def toType[T](implicit mapping: Mapping[S, T]): T = mapping.mapFn(source)
  }

  object MapType {
    def apply[S](source: S): MapType[S] = new MapType[S](source)
  }

  final class AutoMapper[S, T](mapping: Mapping[S, T]) {
    def map(source: S): T = mapping.mapFn(source)
  }

  object AutoMapper {
    def map[S, T]( source: S)(implicit mapping: Mapping[S, T]): T = {
      new AutoMapper[S, T](mapping).map(source)
    }
  }
}

object Hello extends App {
  import Mappings._
  import MyExtensions._
  import example.Mapper.AutoMapper

  val target: Target = Source("Omar", 32)

  val f = Some(null)
  println(f.mapSafe((c:String) => c.substring(3, 5)).mapSafe((c:String) => c.length))

  println("".toOption)

  val t : Target = AutoMapper.map(Source("Omar", 32))
  val t2  = AutoMapper.map(Source("Omar", 32))
  println(t)
  println(t2)




  println("WITH AUTOMAPPER")
  val g = automap(Quote("Omar", 32, util.Arrays.asList("Buenos Aires", "Córdoba", "La Plata"), Source("Omar", 32))).to[QuoteDTO]
  println(g)
}




