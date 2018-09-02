package unit17_AdvancedTypes

object AdvancedTypes {

  class A {}

  def main(args: Array[String]): Unit = {
    val a = new A
    import scala.reflect.runtime.universe._
    println(a.getClass)
    println(typeOf[A])
    println(classOf[A])
  }

}
