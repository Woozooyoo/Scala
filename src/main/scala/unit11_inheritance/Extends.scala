package unit11_inheritance

object Extends {
  def main(args: Array[String]): Unit = {

    println("===================重写方法===================")
    class Animal(age: Int, hairColor: String) {
      def walk = {}
    }

    class Dog(age: Int, hairColor: String) extends Animal(age, hairColor) {
      override /*重写非抽象方法必须要有，不是抽象可以没有*/ def walk: Unit = {}
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

    println("===================匿名子类===================")
    class Person4(val name: String) {
      override def toString = getClass.getName + "[name=" + name + "]"
    }
    val alien = new Person4("Fred") {
      def greeting = "Greetings, Earthling! My name is Fred."
    }
    println(alien.greeting)
    println(alien)

    println("===================抽象类===================")
    //抽象类唯一的意义是被继承 接口是行为和常量的封装，类是行为和属性的封装，抽象类是未实现和已实现的封装
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
      val env: Array[Int] = new Array[Int](range) //可以lazy
    }

    class Ant extends Creature {
      override val range = 20
    }

    class Ant2 extends {
      override val range = 20   //代码块内的提前实例化
    } with Creature   //提前定义语法，在超类的构造器执行之前初始化子类的val字段

    val ant = new Ant
    println(ant.range)
    println(ant.env.length) //0 是因为先初始化Creature时，Ant没有初始化，

    val ant2 = new Ant2
    println(ant2.range)
    println(ant2.env.length)

  }
}
