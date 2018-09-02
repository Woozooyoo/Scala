package unit15_implicit

import java.io.File
import scala.io.Source

object ImplicitDemo1 {
  def main(args: Array[String]): Unit = {
    println("=============1  隐式转换函数 implicit def===============")

    //隐式转换函数是以implicit关键字声明的带有单个参数的函数。
    //这种函数将会自动应用，将值从一种类型转换为另一种类型。
    implicit def double2Int(d: Double) = d.toInt

    val i1: Int = 3.5 //不加上面的implicit就报错
    println(i1) // 3

    println("=============2  利用隐式转换丰富类库功能 implicit def===============")
    //如果需要为一个类增加一个方法，可以通过隐式转换来实现。比如想为File增加一个read方法，可以如下定义：
    class RichFile(val from: File) {
      def read = Source.fromFile(from.getPath).mkString
    }

    implicit def file2RichFile(from: File) = new RichFile(from)

    val contents = new File("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt").read
    println(contents)
    //有什么好处呢？好处就是你可以不修改原版本的代码而为原本的代码增加新功能。

    println("=============3  隐式值 implicit val ===============")
    //将name变量标记为implicit，所以编译器会在方法省略隐式参数的情况下去搜索作用域内的隐式值作为缺少参数。
    implicit val defaultName = "Nick"

    def person(implicit name: String) = name

    println(person)

    //但是如果此时你又相同作用域中定义一个隐式变量，再次调用方法时就会报错：出现二义性
    /*implicit val name2 = "Nick"
    implicit val name3 = "Nick"

    def person2(implicit name: String) = name2

    println(person2)*/

    println("=============4  隐式视图===============")

    //1)  隐式转换为目标类型：把一种类型自动转换到另一种类型
    def foo(msg: String) = println(msg)

    implicit def intToString(x: Int) = x.toString

    foo(10)

    //2)  隐式转换调用类中本不存在的方法
    /**class Dog {  val name = "金毛"}

    class Skill {
      def fly(animal: Dog, skill: String) = println(animal.name + "已领悟" + skill)
    }

    object Learn {
      implicit def learningType(s: Dog) = new Skill
    }*/
    import unit15_implicit.Learn._
    val dog = new Dog
    dog.fly(dog, "飞行技能")
    //当然了，以上操作也可以定义在包对象中，即，在object Learn的外面再套一层，package，没问题的！

    println("=============5 隐式类===============")
    /*在scala2.10 后提供了隐式类， 可以使用implicit声明类， 但是需要注意以下几点：
    -- 其所带的构造参数有且只能有一个
    -- 隐式类必须被定义在“ 类” 或“ 伴生对象” 或“ 包对象” 里
    -- 隐式类不能是case class （ case class在定义会自动生成伴生对象与2矛盾 ）
    -- 作用域内不能有与之相同名称的标示符*/

    import unit15_implicit.StringUtils._
    println("abcd".increment)

  }
}

object StringUtils {
  implicit class StringImprovement(val s: String) { //隐式类
    def increment = s.map(x => (x + 1).toChar)
  }
}
