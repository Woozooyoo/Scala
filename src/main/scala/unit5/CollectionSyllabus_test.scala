package unit5

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * 集合三要素
  * 1定义
  * 2赋值 取值
  * 3追加
  * 4（遍历）
  */
object CollectionSyllabus_test {
  def main(args: Array[String]): Unit = {
    val arr1 = new Array[Int](5)
    println(arr1 mkString " ")

    val arr2 = Array(1, 2, 3)
    println(arr2 mkString " ")
    println(arr2(1))

    arr2(1) = 10
    println(arr2 mkString " ")

    val arr3 = "???" +: arr2
    println(arr3 mkString " ")

    val arrayBuffer = ArrayBuffer[Int](10, 9, 8)
    println(arrayBuffer)
    println(arrayBuffer(1))
    arrayBuffer(1) = -1
    println(arrayBuffer)
    println(arrayBuffer(1))
    arrayBuffer append(1, 2, 3)
    println(arrayBuffer)

    val arrayBuffer2 = arrayBuffer :+ 99
    println(arrayBuffer2)

    val array4 = Array.ofDim[Int](3, 4)
    array4(1)(2) = 1
    for (x <- array4)
      println(x mkString " ")

    val array5 = array4 :+ Array(1, 2, 3)
    for (x <- array5)
      println(x mkString " ")

    val arrayBuffer6 = ArrayBuffer("1", "2", "3")
    import scala.collection.JavaConversions._
    val processBuilder = new ProcessBuilder(arrayBuffer6)
    processBuilder command() add "10"
    println(processBuilder command())

    val strings: mutable.Buffer[String] = processBuilder command()
    println(strings)

    val tuple1 = (1, 2, 3.0f, "heiheihei", 4)
    println(tuple1._4)
    for (e <- tuple1.productIterator)
      print(e + " ")
    println()

    tuple1.productIterator.foreach(print)
    println()
    tuple1.productIterator.foreach(i => print(i + " "))
    println()

    val list1 = List(1, 2)
    val list2 = list1 updated(1, 10)
    println(list1 + " " + list2)
    println(Nil)
    val list2_1 = 1 :: 2 :: 3 :: Nil
    println(list2_1)
    val list2_2 = 1 :: 2 :: 3 :: List(6, 7) :: Nil
    println("list2_2: " + list2_2)
    val list2_3 = 1 :: 2 :: 3 :: List(6, 7)
    println("list2_3: " + list2_3)

    //变长 ListBuffer
    val listBuffer = ListBuffer[Int](3, 0)
    //追加 后还是原有的ListBuffer
    listBuffer append(1, 2)
    println("listBuffer: " + listBuffer)
    //ListBuffer update 和List 的updated不同
    listBuffer update(0, 10)
    println("listBuffer: " + listBuffer)

    //5、队列
    val q1 = scala.collection.mutable.Queue[Int](1, 2)
    println(q1)
    //赋值
    q1(1) = 10
    println(q1)
    //追加元素
    q1 += 20
    println(q1)
    val q2 = new mutable.Queue[Int]()
    //追加集合
    q2 ++= q1
    println("q2: " + q2)
    q1 ++= List(1, 2, 3)
    println(q1)

    //6Map
    val map1 = Map("Alex" -> 21, "Adrian" -> 22)
    println(map1)
    println(map1("Adrian"))
    val stringToInt = map1 updated("Adrian", 90)
    println(stringToInt)
    println(map1)

    val stringToInt2 = mutable.Map[String, Int](("adrian", 22), ("abracadabra", 23))
    println(stringToInt2)
    stringToInt2("Sebastian") = 33
    println(stringToInt2)
    stringToInt2 += ("Jack" -> 24)
    stringToInt2 += ("Sebastian" -> 90)
    println(stringToInt2)

    for (e <- stringToInt2)
      print(e + " ")

    for ((a, v) <- stringToInt2)
      print(a + "," + v + " ")
    println()

    //7 Set
    val set1 = Set(1, 2, 3, 3, 3)
    println(set1)
    println(set1(3))
    val set2 = mutable.Set(1, 2, 2, "3")
    println(set2)
    println(set2("3"))
    val bool = set2 add "3"
    println(bool)
    val unit = set2 += 5
    println(unit)

    /** 8、 集合中元素与函数之间的映射 */
    val listOri = List("Alice", "Bob", "Kotlin")

    //map的作用是对集合中的每个元素 映射 一个方法
    def f1(x: String) = x.toLowerCase()

    val listConvert = listOri.map(f1)
    println(listConvert)

    println(listOri.map((x: String) => x toUpperCase))
    println(listOri.map(x => x toLowerCase))

    println(listOri.map(_.toUpperCase))
    println(listOri.map(x => x.toUpperCase + x.toLowerCase))

    println(listOri flatMap (x => x.toUpperCase() + x.toLowerCase()))

    val listReduced = List(1, 2, 3)
    println(listReduced.reduceLeft(_ - _))
    println(listReduced.reduceLeft((r, x) => r - x))

    println(listReduced.reduceRight(_ - _))

    //fold
    println(listReduced.fold(5)(_ - _))
    println((5 /: listReduced) (_ - _))
    println(listReduced.foldRight(5)(_ - _))
    println((listReduced :\ 5) (_ - _))
    val sentence = "乌云乌云快走开 你可知道我不常带把伞 带把伞 oh~ " +
      "乌云乌云快走开 感觉你在挑战我的乐观 的乐观 oh~"
    val mapCount = (Map[Char, Int]() /: sentence) ((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
    println(mapCount)

    //scan
    println(listReduced.scan(5)(_ - _))
    println(listReduced.scanRight(5)(_ - _))

    //zip
    val phoneList = List("15837312345", "137373123456")
    val nameList = List("孙悟空", "猪八戒", "沙和尚")
    println(phoneList zip nameList)

    //stream
    def numForm(n: String):Stream[String] = {
      n #:: numForm("Sofia")
    }
    val stream = numForm("AlvaroSoler")
    println(stream)
    println(stream.tail)
    println(stream)
    println(stream.tail.tail)
    println(stream)
    println(stream.tail.tail.tail)
    println(stream)//Stream(AlvaroSoler, Sofia, Sofia, Sofia, ?)
    println(stream.tail)
    println(stream.tail.tail)
  }
}
