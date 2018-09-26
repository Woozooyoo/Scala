package unit14_TypeParameters

// 型变
object Variance extends App {

  class Person(name: String) {
    override def toString = name + " | " + getClass.getName
  }

  class Student(name: String) extends Person(name)

  class Pair1[T](val first: T, val second: T) {
    override def toString = "(" + first + "," + second + ")"
  }

  def makeFriends1(p: Pair1[Person]) =
    p.first + " and " + p.second + " are now friends."

  val fred = new Student("Fred")
  println(fred)
  val wilma = new Student("Wilma")
  println(wilma)

  val studentPair1 = new Pair1(fred, wilma)
  println(studentPair1)

  //  makeFriends(studentPair1) // Error

  // Making Pair covariant/*协变*/ allows conversion of a
  // Pair[Student] to a Pair[Person]

  println("============= + T covariant/*协变*/ ===============")

  class Pair2[+T](val first: T, val second: T) {
    override def toString = "(" + first + "," + second + ")"
  }

  def makeFriends2(p: Pair2[Person]) =
    p.first + " and " + p.second + " are now friends."

  val studentPair2 = new Pair2(fred, wilma)

  makeFriends2(studentPair2) // OK
  println(makeFriends2(studentPair2))


  println("============= - T contravariant/*逆变*/===============")
  // This contravariant/*逆变*/ Friend trait allows conversion of a
  // Friend[Person] to a Friend[Student]

  trait Friend[-T] {
    def befriend(someone: T)
  }

  class Person2(name: String) extends Friend[Person2] {
    override def toString = name + " | " + getClass.getName

    def befriend(someone: Person2) {
      this + " and " + someone + " are now friends."
    }
  }

  class Student2(name: String) extends Person2(name)


  def makeFriendWith(s: Student2, f: Friend[Student2]) {
    f.befriend(s)
  }

  val susan = new Student2("Susan")
  val bob = new Person2("Bob")

  makeFriendWith(susan, bob) // Ok, Fred is a Friend of any person

  // A unary function has variance Function1[-A, +R]

  def friends(students: Array[Student2], find: Function1[Student2, Person2]) =
    for (s <- students) yield find(s)

  def friends2(students: Array[Student2], find: Student2 => Person2) =
    for (s <- students) yield find(s)

  def findFred(s: Student2) = new Person2("Fred")

  val persons: Array[Person2] = friends(Array(susan, new Student2("Barney")), findFred)
  println(persons.mkString("\n"))


}
/** 10  型变
  * 术语：
  * 英文	          中文	    示例
  * Variance      型变    	Function[-T, +R]
  * Nonvariant	  不变    	Array[A]
  * Covariant	    协变	    Supplier[+A]  //List[+Son] 是List[+Father]的子类  连带关系
  * Contravariant	逆变	    Consumer[-A]  //List[+Son] 是List[+Father]的父类
  * Immutable	    不可变	  String
  * Mutable	      可变	    StringBuilder
  * 其中，Mutable常常意味着Nonvariant，但是Noncovariant与Mutable分别表示两个不同的范畴。
  * 即：可变的，一般意味着“不可型变”，但是“不可协变”和可变的，分别表示两个不同范畴。
  * *
  * 型变(Variance)拥有三种基本形态：协变(Covariant), 逆变(Contravariant), 不变(Nonconviant)，可以形式化地描述为：
  * 一般地，假设类型C[T]持有类型参数T；给定两个类型A和B，如果满足A < : B，则C[A]与 C[B]之间存在三种关系：
  * 如果C[A] < : C[B]，那么C是协变的(Covariant);
  * 如果C[A] : > C[B]，那么C是逆变的(Contravariant);
  * 否则，C是不变的(Nonvariant)。
  * Scala的类型参数使用+标识“协变”，-标识“逆变”，而不带任何标识的表示“不变”(Nonvariable)：
  * trait C[+A]   // C is covariant
  * trait C[-A]   // C is contravariant
  * trait C[A]    // C is nonvariant
  *
  * 如何判断一个类型是否有型变能力：
  * 一般地，“不可变的”(Immutable)类型意味着“型变”(Variant)，而“可变的”(Mutable)意味着“不变”(Nonvariant)。
  * 其中，对于不可变的(Immutable)类型C[T]
  * 如果它是一个生产者，其类型参数应该是协变的，即C[+T]；
  * 如果它是一个消费者，其类型参数应该是逆变的，即C[-T]。
  */