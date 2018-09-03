package unit14_TypeParameters

object TypeParameters {
  def main(args: Array[String]): Unit = {
    println("=============3 上界 左小于等于右===============")
    class Pair1[T <: Comparable[T]](val first: T, val second: T) {
      def smaller = if (first.compareTo(second) < 0) first else second
    }

    val p = new Pair1("Fred", "Brooks")
    println(p.smaller)

    println("=============3 下界 左大于等于右===============")
    class Pair2[T](val first: T, val second: T) {
      def replaceFirst[R >: T](newFirst: R) = new Pair2[R](newFirst, second)

      override def toString = "(" + first + "," + second + ")"
    }

    val p2 = new Pair2("Nick", "Alice")
    println(p2)
    println(p2.replaceFirst("Joke"))
    println(p2)

    println("=============5 View Bounds视图界定 T隐式转换成右边 [T <% Comparable[T]]===============")
    //由于Scala的Int类型没有实现Comparable接口，
    // 所以我们需要将Int类型隐式的转换为RichInt类型，比如：
    class Pair5[T](val first: T, val second: T)(implicit comp: T => Comparable[T]) {
    //class Pair5[T <% Comparable[T]](val first: T, val second: T) {

        def smaller = if (first.compareTo(second) < 0) first else second

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

    class Pair6[T: Ordering](val first: T, val second: T) {
      def smaller(implicit ord: Ordering[T]) = {
        println(ord)
        if (ord.compare(first, second) < 0) first else second
      }

      override def toString = "(" + first + "," + second + ")"
    }

    val p6 = new Pair6(1, 2)
    println(p6.smaller)

    println("=============7  Manifest上下文界定===============")
    /**                                Manifest[T] 可以保存泛型T在m变量中,泛型擦出后用manifest[?]与m比较*/
    def foo[T](x: List[T])(implicit m: Manifest[T]) = {
      println(m)
      if (m <:< manifest[String])   //<:<是小于等于后面xxx类型
        println("Hey, this list is full of strings")
      else
        println("Non-stringy list")
    }

    foo(List("one", "two"))
    foo(List(1, 2))
    foo(List("one", 2))

    println("=============7  TypeTag上下文界定===============")
    /*=:=，意思为：type equality
        <:< ,意思为：subtype relation
          类型判断不要用 == 或 !=*/

    val list1 = List(1, 2, 3)
    val list2 = List("1", "2", "3")
    val list3 = List("1", "2", 3)

    class Fuck{}
    def test1(x: List[Any]) = {
      x match {
        case list: List[Fuck] => "Fuck list"
        case list: List[String] => "String list"
        case list: List[Any] => "Any list"
      }
    }

    println(test1(list1))
    println(test1(list2))
    println(test1(list3))

    //精准匹配
    import scala.reflect.runtime.universe._
    /** A:TypeTag 可以在泛型被擦除时,保存一份A类型在TypeTag中,可以调用typeOf查看 */
    def test2[A: TypeTag](x: List[A]) = typeOf[A] match {
      case t if t =:= typeOf[String] => "String List"
//      case t if t =:= typeOf[Animal] => "Animal List"
      case t if t <:< typeOf[Animal] => "小于等于Animal List"//<:<是小于等于后面xxx类型
      case t if t =:= typeOf[Int] => "Int List"
    }

    println(test2(List("string")))
    println(test2(List(new Animal)))
    println(test2(List(new Dog)))
    println(test2(List(1, 2)))
    //List支持 协变     type List[+A] = scala.collection.immutable.List[A]

  }
  class Animal {}
  class Dog extends Animal {}

  /**10  型变
    术语：
  英文	          中文	    示例
  Variance      型变    	Function[-T, +R]
  Nonvariant	  不变    	Array[A]
  Covariant	    协变	    Supplier[+A]  //List[+Son] 是List[+Father]的子类  连带关系
  Contravariant	逆变	    Consumer[-A]  //List[+Son] 是List[+Father]的父类
  Immutable	    不可变	  String
  Mutable	      可变	    StringBuilder
  其中，Mutable常常意味着Nonvariant，但是Noncovariant与Mutable分别表示两个不同的范畴。
  即：可变的，一般意味着“不可型变”，但是“不可协变”和可变的，分别表示两个不同范畴。

  型变(Variance)拥有三种基本形态：协变(Covariant), 逆变(Contravariant), 不变(Nonconviant)，可以形式化地描述为：
  一般地，假设类型C[T]持有类型参数T；给定两个类型A和B，如果满足A < : B，则C[A]与 C[B]之间存在三种关系：
  如果C[A] < : C[B]，那么C是协变的(Covariant);
  如果C[A] : > C[B]，那么C是逆变的(Contravariant);
  否则，C是不变的(Nonvariant)。
  Scala的类型参数使用+标识“协变”，-标识“逆变”，而不带任何标识的表示“不变”(Nonvariable)：
  trait C[+A]   // C is covariant
  trait C[-A]   // C is contravariant
  trait C[A]    // C is nonvariant
  如何判断一个类型是否有型变能力：
  一般地，“不可变的”(Immutable)类型意味着“型变”(Variant)，而“可变的”(Mutable)意味着“不变”(Nonvariant)。
  其中，对于不可变的(Immutable)类型C[T]
    如果它是一个生产者，其类型参数应该是协变的，即C[+T]；
  如果它是一个消费者，其类型参数应该是逆变的，即C[-T]。
*/

}
