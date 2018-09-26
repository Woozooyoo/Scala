package unit14_TypeParameters

object TypeParameters {
  def main(args: Array[String]): Unit = {
    println("=============1  泛类型===============")
    //类和特质都可以带类型参数，用方括号来定义类型参数，可以用类型参数来定义变量、方法参数和返回值。带有一个或多个类型参数的类是泛型的。如下p1，如果实例化时没有指定泛型类型，则scala会自动根据构造参数的类型自动推断泛型的具体类型。
    class Pair[T, S](val first: T, val second: S) {
      override def toString = "(" + first + "," + second + ")"
    }
    //从构造参数推断类型
    println(new Pair(42, "String"))
    //设置类型
    println(new Pair[Any, Any](42, "String"))

    println("=============2  泛型函数===============")

    //函数或方法也可以有类型（泛型）参数。
    def getMiddle[T](a: Array[T]) = a(a.length / 2)
    // 从参数类型来推断类型
    println(getMiddle(Array("Bob", "had", "a", "little", "brother")).getClass.getTypeName)
    //指定类型，并保存为具体的函数。
    val f = getMiddle[String] _
    println(f(Array("Bob", "had", "a", "little", "brother")))


    println("=============3 上界限定 左小于等于右===============")
    //限定只有   T 类型是 Comparable的子类的才能通过
    class Pair1[T <: Comparable[T]](val first: T, val second: T) {
      def smaller = if (first.compareTo(second) < 0) first else second
    }

    val p = new Pair1("Fred", "Brooks")
    println(p.smaller)

    println("=============3 下界 左大于等于右===============")
    class Pair3[T](val first: T, val second: T) {
      def replaceFirst[R >: T](newFirst: R) = new Pair3[R](newFirst, second)
      override def toString = "(" + first + "," + second + ")"
    }

    val p2 = new Pair3("Nick", "Alice")
    println(p2)
    println(p2.replaceFirst("Joke"))
    println(p2)

    class Person(name: String) { override def toString = getClass.getName + " " + name }

    class Student(name: String) extends Person(name)

    val fred = new Student("Fred")
    val wilma = new Student("Wilma")
    val barney = new Person("Barney")

    val p3 = new Pair3(fred, wilma)
    p3.replaceFirst(barney) // A Pair[Person] [R >: T](newFirst: R)  OK  Person >: Student

    // Don't omit忽略 the upper bound:

    class Pair4[T](val first: T, val second: T) {
      def replaceFirst[R](newFirst: R) = new Pair(newFirst, second)
      override def toString = "(" + first + "," + second + ")"
    }

    val p4 = new Pair4(fred, wilma)
    p4.replaceFirst(barney) // A Pair[Any]


    println("=============5 View Bounds视图界定 T隐式转换成右边 [T <% Comparable[T]]===============")
    //由于Scala的Int类型没有实现Comparable接口，
    // 所以我们需要将Int类型隐式的转换为RichInt类型，比如：
    class Pair5[T](val first: T, val second: T)(implicit comp: T => Comparable[T]) {  //跟 <% 同理
      //class Pair5[T <% Comparable[T]](val first: T, val second: T) {              //跟 implicit 同理

      def smaller = if (first.compareTo(second) < 0) first else second    //compareTo 有下划线 说明发生隐式转换

      override def toString = "(" + first + "," + second + ")"
    }

    val p5 = new Pair5(4, 2)
    println(p5.smaller)

    println("=============6  Context Bounds 上下文界定 [T <% Comparable[T]]===============")
    /*视图界定 T <% V要求必须存在一个从T到V的隐式转换。
    上下文界定的形式为T:M，其中M是另一个泛型类，
    它要求必须存在一个类型为M[T]的隐式值。
    下面类定义要求必须存在一个类型为Ordering[T]的隐式值，
    当你使用了一个使用了隐式值得方法时，传入该隐式参数。*/

    class Pair6[T: Ordering](val first: T, val second: T)/*(implicit ord: Ordering[T])*/ {
      // implicit def int2Ordering(int:Int):Ordering[Int]={}
      def smaller(implicit ord: Ordering[T]) = {
        println(ord)
        if (ord.compare(first, second) < 0) first else second
      }

      override def toString = "(" + first + "," + second + ")"
    }

    val p6 = new Pair6(1, 2)
    println(p6.smaller)
    val q6 = new Pair6("Fred", "Brooks")
    println(q6.smaller)

    println("=============7  Manifest上下文界定 deprecated ===============")

    /** Manifest[T] 可以保存泛型T在m变量中,泛型擦出后用manifest[?]与m比较 */
    def foo[T](x: List[T])(implicit m: Manifest[T]) = {
      println(m)
      if (m <:< manifest[String]) //<:<是小于等于后面xxx类型
        println("Hey, this list is full of strings")
      else
        println("Non-stringy list")
    }

    foo(List("one", "two"))
    foo(List(1, 2))
    foo(List("one", 2))

    println("=============7  TypeTag上下文界定  TypeTag替代了Manifest ClassTag替代了ClassManifest===============")
    /*=:=，意思为：type equality
        <:< ,意思为：subtype relation T类型是否为U或U的子类型
          类型判断不要用 == 或 !=
      T <%< U意思为：T类型是否被隐式（视图）转换为U*/

    val list1 = List(1, 2, 3)
    val list2 = List("1", "2", "3")
    val list3 = List("1", "2", 3)

    class Fuck {}
    def test1(x: List[Any]) = {
      x match {
        case _: List[Fuck] => "Fuck list"
        case _: List[String] => "String list"
        case _: List[Any] => "Any list"
      }
    }

    println(test1(list1))
    println(test1(list2))
    println(test1(list3))

    /**精准匹配*/
    import scala.reflect.runtime.universe._
    /** A:TypeTag 可以在泛型被擦除时,保存一份A类型在TypeTag中,可以调用typeOf查看 */
    def test2[A: TypeTag](x: List[A]) = typeOf[A] match {
      case t if t =:= typeOf[String] => "String List"
      //      case t if t =:= typeOf[Animal] => "Animal List"
      case t if t <:< typeOf[Animal] => "小于等于Animal List" //<:<是小于等于后面xxx类型
      case t if t =:= typeOf[Int] => "Int List"
    }

    println(test2(List("string")))
    println(test2(List(new Animal)))
    println(test2(List(new Dog)))
    println(test2(List(1, 2)))
    //List支持 协变     type List[+A] = scala.collection.immutable.List[A]

    println("=============7  The ClassTag Context Bound===============")
    import scala.reflect._

    def makePair[T : ClassTag](first: T, second: T) = {
      val r = new Array[T](2); r(0) = first; r(1) = second; r
    }

    val a = makePair(4, 2) // An Array[Int]
    println(a.getClass) // In the JVM, [I; is an int[] array

    val b = makePair("Fred", "Brooks") // An Array[String]
    println(b.getClass) // In the JVM, [Ljava.lang.String; is a String[] array


    println("=============8  Type Constraints===============")
    class Pair8[T](val first: T, val second: T) {
      def smaller(implicit ev: T <:< Ordered[T]) =
        if (first < second) first else second
    }

    import java.net.URL

    val p8 = new Pair8(new URL("http://scala-lang.org"), new URL("http://horstmann.com"))
    // Ok as long as we don't call smaller

    //p8.smaller // Error

    // <:< is used in the definition of the Option.orNull method

    val friends = Map("Fred" -> "Barney")
    val friendOpt = friends.get("Wilma") // An Option[String]
    println(friendOpt)
    val friendOrNull = friendOpt.orNull // A String or null
    println(friendOrNull)

    val scores = Map("Fred" -> 42)
    val scoreOpt = scores.get("Fred") // An Option[Int]
    // val scoreOrNull = scoreOpt.orNull // Error

    // <:< can improve type inference

    def firstLast1[A, C <: Iterable[A]](it: C) = (it.head, it.last)

    //firstLast1(List(1, 2, 3)) // Error

    def firstLast2[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
      (it.head, it.last)

    println(firstLast2(List(1, 2, 3))) // OK
  }

  class Animal {}

  class Dog extends Animal {}

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
    * 如何判断一个类型是否有型变能力：
    * 一般地，“不可变的”(Immutable)类型意味着“型变”(Variant)，而“可变的”(Mutable)意味着“不变”(Nonvariant)。
    * 其中，对于不可变的(Immutable)类型C[T]
    * 如果它是一个生产者，其类型参数应该是协变的，即C[+T]；
    * 如果它是一个消费者，其类型参数应该是逆变的，即C[-T]。
    */

}
