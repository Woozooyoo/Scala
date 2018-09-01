package unit09_object

object Object extends App {
  println("===================单例对象===================")

  object Dog {
    println("已初始化...")
    private var leg = 0

    def plus() = {
      leg += 1
      leg
    }
  }

  println(Dog.plus() + Dog.plus()) //1+2=3


  println("===================单例对象2===================")

  class SingletonObject private /*单例的重点*/ (val sex: String, name: String) {
    def describe = {
      println("Sex:" + sex + " Name:" + name)
    }
  }

  object SingletonObject {
    var instance: SingletonObject = null

    def apply(name: String) = {
      if (instance == null) {
        instance = new SingletonObject("Man", name)
      }
      instance
    }
  }

  SingletonObject("Nick").describe
  SingletonObject("Thomas").describe


  println("===================实现枚举extends Enumeration===================")

  object TrafficColorLighting extends Enumeration {
    val YELLOW = Value(1, "yellow") //直接Value
    val RED = Value(3, "red")
    val GREEN = Value(2, "green")
  }

  println(TrafficColorLighting.RED + ","
    + TrafficColorLighting.RED.id)
  println(TrafficColorLighting.YELLOW + ","
    + TrafficColorLighting.YELLOW.id)
  println(TrafficColorLighting.GREEN + ","
    + TrafficColorLighting.GREEN.id)


}
