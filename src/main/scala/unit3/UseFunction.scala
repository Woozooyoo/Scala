package unit3

import scala.math._

object UseFunction { //object 是UseFunction的伴生类对象，包含在内的是静态的
  def main(args: Array[String]): Unit = {
    val a = 100
    println(sqrt(a))
    println(BigInt.probablePrime(10, scala.util.Random))

    println("hello world".distinct)//去重

    println("hello" (4))
    println("hello".apply(4))
    val arr = Array(1, 2, 3)
    println(arr.mkString(" "))
    println(Array(1, 2, 3) mkString "")

    arr(0) = 0
    println(arr mkString "")
    println(arr(0))

    //option  是一个集合
    val map = Map apply("name" -> "Lion", "age" -> 22)
    val value = map("age")
    println(value + "22")
    val option = map.get("sex")
    println(option)
  }
}
