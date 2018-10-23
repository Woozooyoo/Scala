package unit04_function

/**
  * Ordered 使类可以具有比较性 等同于Comparable
  * Ordering 是比较器 可以重写 compare方法 传入两个对象为参数 进行比较 等同于Comparator
  */
object OrderedOrdering extends App {

  import scala.util.Sorting

  val pairs = Array(("a", 5, 2), ("c", 3, 1), ("b", 1, 3))
  println(pairs.mkString)

  // sort by 2nd element
  Sorting.quickSort(pairs)(Ordering.by[(String, Int, Int), Int](_._2))
  println(pairs.mkString)

  // sort by the 3rd element, then 1st
  Sorting.quickSort(pairs)(Ordering[(Int, String)].on(x => (x._3, x._1)))
  println(pairs.mkString)

  /** 对于Person类，如何做让其对象具有可比较性呢？
    * 我们可使用Ordered对象的函数orderingToOrdered做隐式转换，但还需要组织一个Ordering[Person]的隐式参数： */
  case class Person(name: String, age: Int)

  implicit object PersonOrdering extends Ordering[Person] {
    override def compare(p1: Person, p2: Person): Int = {
      p1.name == p2.name match {
        case false => -p1.name.compareTo(p2.name)
        case _ => p1.age - p2.age
      }
    }
  }

  val p1 = Person("rain", 13)
  val p2 = Person("rain", 14)

  import Ordered._

  println(p1 < p2) // True



  val p3 =  Person("rain", 24)
  val p4 =  Person("rain", 22)
  val p5 =  Person("Lily", 15)
  val list = List(p3, p4, p5)

  println(list.sorted)
  // res3: List[Person] = List(name: rain, age: 22, name: rain, age: 24, name: Lily, age: 15)

  list.sortWith { (p1: Person, p2: Person) =>
    p1.name == p2.name match {
      case false => -p1.name.compareTo(p2.name) < 0
      case _ => p1.age - p2.age < 0
    }
  }
  // res4: List[Person] = List(name: rain, age: 22, name: rain, age: 24, name: Lily, age: 15)
}
