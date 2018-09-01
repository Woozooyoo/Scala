package unit11_inheritance

object Extends {
  def main(args: Array[String]): Unit = {

    println("===================重写方法===================")
    class Animal(age: Int, hairColor: String) {
      def walk = {}
    }

    class Dog(age: Int, hairColor: String) extends Animal(age, hairColor) {
      override /*重写非抽象方法必须要有，不是可以没有*/ def walk: Unit = {}
      override def toString = "Age：" + age + ",hairColor：" + hairColor + "," + age
    }

    val dog = new Dog(22, "黑色")
    println(dog)

    println("===================覆写字段===================")
    class PersonFather(val name: String, var age: Int) {
      println("主构造器已经被调用")
      val school = "五道口职业技术学院"
      def sleep = "8 hours"
      override def toString = "我的学校是：" + school + ",我的名字和年龄是：" + name + "," + age
    }


    class PersonSon(name:String, age:Int) extends PersonFather(name, age){
      override val school: String = "清华大学"
    }

    val person = new PersonSon("nick", 20)
    println(person)

    println("===================抽象类===================")
    abstract class Father(age: Int) {
      val id:Int
      val name: String
      def play:Unit
    }

    class Child(age: Int) extends Father(age){
      override val id: Int = 1
      override val name: String = "Nick"

      override def play: Unit = {
        println(name)
      }
    }

    val child = new Child(20)
    child.play

    println("===================构造顺序，提前定义===================")
    class Creature {
      val range: Int = 10
      val env: Array[Int] = new Array[Int](range)
    }

    class Ant extends Creature {
      override val range = 20
    }

    class Ant2 extends {
      override val range = 20
    } with Creature

    val ant = new Ant
    println(ant.range)
    println(ant.env.length)

    val ant2 = new Ant2
    println(ant2.range)
    println(ant2.env.length)

  }
}
