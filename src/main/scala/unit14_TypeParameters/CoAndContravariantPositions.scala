package unit14_TypeParameters

object CoAndContravariantPositions extends App{

  println("============= Co- and Contravariant Positions  协变和逆变点===============")
  /*class Person(name: String) {
    override def toString = name + " | " + getClass.getName
  }

  class Student(name: String) extends Person(name)

  val length = 10
  val students1 = new Array[Student](length)
  val people: Array[Person] = students1 ///////Not legal, but suppose it was . . .
  // Let's force it, so you can see what happens
  val people1: Array[Person] = students1.asInstanceOf[Array[Person]]
  people1(0) = new Person("Fred") ///////Oh no! Now students(0) isn’t a Student

  val people2 = new Array[Person](length)
  val students2: Array[Student] = people2 /////// Not legal, but suppose it was . . .
  // Let's force it, so you can see what happens
  val students3: Array[Student] = people2.asInstanceOf[Array[Student]]

  // You cannot define a covariant mutable pair

  class Pair[+T](var first: T, var second: T) /////// Error

  // This pair is immutable
  class Pair1[+T](val first: T, val second: T) {
    // But newFirst is in a contravariant position
    def replaceFirst(newFirst: T) = new Pair1[T](newFirst, second) /////// Error
  }

  class Pair2[+T](val first: T, val second: T) {
    // Remedy: Another type parameter
    def replaceFirst[R >: T](newFirst: R) = new Pair2[R](newFirst, second)
  }*/


  println("============= 11 Objects Can’t Be Generic 对象不能泛型===============")
  abstract class List[+T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }

  class Node[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty = false
  }

  object Empty extends List[Nothing] {
    // It can't be object Empty[T] extends List[T]
    // OK to be class Empty[T] extends List[T]
    def isEmpty = true
    def head = throw new UnsupportedOperationException
    def tail = throw new UnsupportedOperationException
  }


  def show[T](lst: List[T]) {
    if (!lst.isEmpty) { println(lst.head); show(lst.tail) }
  }

  val list = new Node(42, Empty)
  show(new Node(1729, list))
}