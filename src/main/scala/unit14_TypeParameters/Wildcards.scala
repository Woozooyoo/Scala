package unit14_TypeParameters

object Wildcards extends App{
  println("============= Wildcards 类型通配符 ===============")
  class Pair[T](var first: T, var second: T) {
    override def toString = "(" + first + "," + second + ")"
  }

  class Person(val name: String) {
    override def toString = name + " | " + getClass.getName
  }

  class Student(name: String) extends Person(name)

  def makeFriends(p: Pair[_ <: Person]) =
    p.first.name + " and " + p.second.name + " are now friends."

  val fred = new Student("Fred")
  val wilma = new Student("Wilma")

  val studentPair = new Pair(fred, wilma)

  makeFriends(studentPair) // OK

  import java.util.Comparator
  def min1[T](p: Pair[T])(comp: Comparator[_ >: T]) =
    if (comp.compare(p.first, p.second) < 0) p.first else p.second

  // Just a silly example
  val sillyHashComp = new Comparator[Object] {
    def compare(a: Object, b: Object) = a.hashCode() - b.hashCode()
  }

  "Fred".hashCode
  "Wilma".hashCode

  // Note that the comparator uses a supertype of T = String
  min1(new Pair("Fred", "Wilma"))(sillyHashComp)



  // This should work, but it doesn't in Scala 2.12
  /*def min2[T <: Comparable[_ >: T]](p: Pair[T]) =
    if (p.first.compareTo(p.second) < 0) p.first else p.second*/

  // Here is a workaround

  type SuperComparable[T] = Comparable[_ >: T]
  def min[T <: SuperComparable[T]](p: Pair[T]) =
    if (p.first.compareTo(p.second) < 0) p.first else p.second

  class Person2(val name: String) extends Comparable[Person2] {
    override def toString = getClass.getName + " " + name
    def compareTo(other: Person2) = name.compareTo(other.name)
  }

  class Student2(name: String) extends Person2(name)

  // Note that Student <: Comparable[Person]

  val bob = new Student2("Bob")
  val susan = new Student2("Susan")

  val studentPair2 = new Pair(bob, susan)
//  min2(studentPair2)

}
