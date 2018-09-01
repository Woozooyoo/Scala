package unit11

object ExtendsSyllabus {
  def main(args: Array[String]): Unit = {
    val dog = new Dog(22, "黑色")
    println(dog)

    //覆写字段
    val person = new Person2("nick", 20)
    println(person)

    //抽象类
    val child = new Child(20)
    child.play

    //构造顺序，提前定义
    val ant  = new Ant
    println(ant.range)
    println(ant.env.length)

    val ant2 = new Ant2
    println(ant2.range)
    println(ant2.env.length)

  }
}
