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
object CollectionSyllabus {
  def main(args: Array[String]): Unit = {
    //定长数组Array
    val arr1 = new Array[Int](10)
    println(arr1 mkString " ") //0 0 0 0 0 0 0 0 0 0

    val arr2 = Array(1, 2, 3)
    println(arr2 mkString " ") //1 2 3
    //取值
    println(arr2(1)) //2
    //赋值  定长数组里面的值可改变
    arr2(1) = 10 //arr2 update(1,10)
    println(arr2 mkString " ") //1 10 3

    //Array :+ 追加元素  产生新的集合对象 如果追加数组 则变成追加数组地址值
    //    val arr3 = arr1 :+ "???"
    val arr3 = "???" +: arr2
    println(arr3 mkString " ") //??? 1 10 3

    //2 变长数组ArrayBuffer
    //小括号可以存放初始化的元素内容
    val arrBuffer1 = ArrayBuffer[Int](10, 9, 8)
    println(arrBuffer1) //ArrayBuffer(10, 9, 8)
    //赋值取值
    println(arrBuffer1(1))
    arrBuffer1(1) = -1
    println(arrBuffer1) //ArrayBuffer(10, -1, 8)
    println(arrBuffer1(1))
    //追加 append 追加到原arrayBuffer
    arrBuffer1 append(1, 2, 3)
    println(arrBuffer1) //ArrayBuffer(10, -1, 8, 1, 2, 3)

    //追加出新的arrayBuffer :+
    val arrBuffer2 = arrBuffer1 :+ 99
    println(arrBuffer2) //ArrayBuffer(10, -1, 8, 1, 2, 3, 99)

    // 定义多维数组
    val arr4 = Array.ofDim[Int](3, 4) //一定要有泛型
    arr4(1)(2) = 1
    for (x <- arr4) {
      println(x mkString " ")
    }
    println(arr4(1)(2))

    //追加出新数组 :+
    val arr5 = arr4 :+ Array(1, 2, 3, 4)
    for (x <- arr5) {
      println(x mkString " ")
    }
    println("******上面是//1、数组")

    //2 scala和java的互相转换
    val arr6 = ArrayBuffer("1", "2", "3") //转换的一定元素是String
    //scala to Java
    import scala.collection.JavaConversions._
    val javaArr = new ProcessBuilder(arr6)
    //javaArr.command()是 List
    javaArr.command().add("10")
    println("javaArr.command():" + javaArr.command()) //[1, 2, 3, 10]

    //java to scala
    val scalaArr: mutable.Buffer[String] = javaArr.command()
    println(scalaArr) //ArrayBuffer(1, 2, 3, 10)
    println("******上面是//2、互转")

    //3 元组
    //创建
    val tuple1 = (1, 2, 3.0F, "heiheihei", 4)
    //特殊的 取值
    println("tuple1._4:" + tuple1._4) //tuple1._4:heiheihei
    //tuple的遍历  productIterator
    for (e <- tuple1.productIterator) {
      print(e + " ")
    } // 1 2 3.0 heiheihei 4
    println()

    tuple1.productIterator.foreach(print)
    println()
    tuple1.productIterator.foreach(i => print("tuple1中的元素：" + i + " "))
    println("\n******上面是//4、元祖")

    //4、List 定长
    val list1 = List(1, 2)
    //取值
    println("list1(1): " + list1(1))
    //特殊：定长List只有 updated赋值成新的list1_new
    val list1_new = list1 updated(1, 10)
    println("list1: " + list1)
    println("list1_new: " + list1_new)
    //追加 成新的List :: 从右往左创建  ::是追加
    println("Nil: " + Nil)
    val list2_1 = 1 :: 2 :: 3 :: Nil
    println("list2_1: " + list2_1)
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
    println("******上面是//4、List")

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

    q1 enqueue(99, 98)
    println(q1)

    //删除 dequeue 先进先出，删除第一个
    q1 dequeue()
    println(q1)

    println(q1 head)
    println(q1 last)
    println(q1 tail) //the queue except the first one    expect
    println("******上面是//5、队列")

    //6、Map key 和value的类型都无所谓
    val map1 = Map("Alice" -> 22, "Bob" -> 21, "Kotlin" -> 20)
    println(map1)
    //取值
    println(map1("Alice")) //22
    //updated 修改 产生新Map
    println(map1 updated("Alice", 90))
    println(map1)
    //变长Map 用泛型约束
    val mutableMap = mutable.Map[String, Int](("Alice", 22), ("Bob", 21))
    println(mutableMap)
    //有则更新，无则追加
    mutableMap("Kotlin") = 50
    println(mutableMap)

    //赋值--追加 mutable 的+=  是有则更新，无则追加
    mutableMap += ("Alice" -> 80)
    mutableMap += ("ABC" -> 10)
    println(mutableMap)

    //遍历
    for (m <- mutableMap) {
      print(m + ", ")
    }
    println()

    for ((k, v) <- mutableMap) {
      print(k + ":" + v + ", ")
    }
    println("\n******上面是6、Map")

    //7、Set
    val set1 = Set(1, 2, 2, 3) //去重
    println(set1)
    val mutableSet = mutable.Set(1, 2, 2, "3")
    println(mutableSet)
    //没有取值 只有判断值是否存在
    println(set1(0)) //false //等同于contains(value)
    //Set只有追加 删除 value
    mutableSet add 4
    println(mutableSet)
     //等同于add 但是返回的是添加后的Set
    println(mutableSet += 5)
    mutableSet -= 1
    mutableSet remove 4 //等同与-=
    println(mutableSet)

    //遍历
    for (s <- mutableSet)
      print(s + " ")
    println("\n******上面是7、Set")

    /** 8、 集合中元素与函数之间的映射 */
    val listOri = List("Alice", "Bob", "Kotlin")

    //map的作用是对集合中的每个元素 映射 一个方法
    def f1(x: String): String = x.toLowerCase()

    val listConvert = listOri.map(f1)
    println(listConvert)
    //省略函数名 匿名函数  也省略了返回类型
    val listConvert1 = listOri.map((x: String) => x toLowerCase)
    println(listConvert1)
    //省略参数的类型
    val listConvert2 = listOri.map(x => x toUpperCase)
    println(listConvert2)

    //"_"代表每一个元素，这个元素只能被使用一次
    val list_Simple = listOri.map(_.toLowerCase)
    println(list_Simple) //List(alice, bob, kotlin)
    //设为变量 可以使用多次
    val listConvert3 = listOri.map(x => x.toUpperCase + x.toLowerCase)
    println(listConvert3) //List(ALICEalice, BOBbob, KOTLINkotlin)

    //flatMap 压散压碎Map
    println(listOri flatMap (x=>x.toUpperCase+x.toLowerCase))
    val names = List("Alice", "Bob", "Nick")
    val chars = names.flatMap(_.toUpperCase()) //chars: List(A, L, I, C, E, B, O, B, N, I, C, K)
    println("chars: " + chars)
    println("******上面是8、集合元素 与函数映射")

    /** 9 化简 折叠 扫描 */
    //Reduce
    val listReduced = List(1, 2, 3)
    //1-2) -3)
    //println(listReduced.reduceLeft(_ - _))
    println(listReduced.reduceLeft((r, x) => r - x))
    //(1- (2-3
    println(listReduced.reduceRight(_ - _))
    println("******上面是reduce方法，就是传0初始值的fold方法")

    //Fold
    val listFolded = List(1, 2, 3)
    //fold=foldLeft   5 是初始值  foldLeft就是以一个初始值 为最左边的数
    //                                    从左到右 进行函数
    println(listFolded.fold(5)((sum, y) => sum - y)) //5-1 -2 -3  = -1
    println((5 /: listFolded) (_ - _))
    println((5 /: listFolded) ((res, next) => res - next)) //5-1 -2 -3  = -1

    //(1- (2- (3-5=-3  以下三个结果相同 foldRight就是以一个初始值为右边的数
    //                                    从右到左 进行函数
    println(listFolded.foldRight(5)((result, y) => result - y))
    //foldRight
    println(listFolded.foldRight(5)(_ - _))
    println((listFolded :\ 5) (_ - _))

    println(Map[Char, Int]())
    //统计一句话中，各个文字出现的次数
    val sentence = "一首现代诗《笑里藏刀》:哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈刀哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
    //m +  (“一” -> 1, “首” -> 1, “哈” -> 1)
    //初始值是Map() foldLeft sentence
    //((m是上一步的结果Map(), c是下一个元素"一") => m是Map() + 映射关系(c "一" -> (m.getOrElse(c, 0)有就得到次数，else没有就默认给(c,0)的映射 + 1)))
    val i7 = (Map[Char, Int]() /: sentence) ((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
    println(i7)
    println("******上面是fold方法")

    //    这个理解需要结合上面的知识点，扫描，即对某个集合的所有元素做fold操作，但是会把产生的所有中间结果放置于一个集合中保存。
    println(listReduced.scanLeft(0)(_ + _))
    println(listReduced.scan(5)(_ - _))
    println(listReduced.scanRight(5)(_ - _))
    println("******上面是scan方法")

    //Curry //调用第一个匿名函数，它的变量是第二个匿名函数
    val myFunction = (x: Int) => (y: Int) => x - y
    println(myFunction(10))
    println(myFunction(10)(20))

    //Curry化
    def myFunctionCurry(x: Double)(y: Double) = x / y

    println(myFunctionCurry(10)(20))

    //10、拉链
    val phoneList = List("15837312345", "137373123456")
    val nameList = List("孙悟空", "猪八戒", "沙和尚")
    val zlist1 = phoneList /*在左*/ zip nameList
    println(phoneList /*在左*/ zip nameList) /*在右*/
     //二元组的List List((15837312345,孙悟空), (137373123456,猪八戒))
    println(nameList zip nameList)
    println(nameList zip phoneList)
    val namePhoneMap = mutable.Map[String, String]()
    for (e <- zlist1) {
      namePhoneMap += e
    }
    println(namePhoneMap) //Map(15837312345 -> 孙悟空, 137373123456 -> 猪八戒)
    println("******上面是10拉链方法，就是融合两个List的方法")

    //12、stream 用到多大的区间，就会动态的生产，使用后才缓存 末尾元素遵循lazy规则。
    def numForm(n: Int): Stream[Int] = {
      n #:: numForm(n + 1)
    }
    val numStream=numForm(0)
    //tail 从初始状态开始 进行一个方法(+1) 并排除第一个返回
    println(numStream)      //Stream(0, ?)
    println(numStream.tail) //Stream(1, ?)
    println(numStream)      //Stream(0, 1，?)
    println(numStream.tail.tail.tail) //Stream(3, ?)  从0开始 一直排除第一个
    println(numStream)      //Stream(0, 1, 2, 3, ?) 打印出最新的状态
    println(numStream.tail.tail.tail.tail) //Stream(4, ?)
    println(numStream)      //Stream(0, 1, 2, 3, 4, ?)
    println(numStream.tail) //Stream(1, 2, 3, 4, ?)
    println(numStream)      //Stream(0, 1, 2, 3, 4, ?)
    println(numStream.tail.tail) //Stream(2, 3, 4, ?)
    println(numStream)      //Stream(0, 1, 2, 3, 4, ?)

    val numStream1=numForm(0)
    val s1=numStream1.tail
    println(s1)   //Stream(1, ?)
    val s2=s1.tail
    println(s1)   //Stream(1, 2, ?)
    println(s2)   //Stream(2, ?)

    println("******上面是12 Stream方法，就是从初始状态开始tail更新并排除第一个")

    //13、view 使用完后不缓存 只保留函数 每次调用时才加载
//    val view = (1L to 10000000L).view.map(x => x).filter(x => x.toString.reverse == x.toString)
//    println(view) //SeqViewMF(...)
//    println(view.mkString(" "))
//    println(view.mkString(" "))

//    val result1 = (0 to 10000000).map { case _ => Thread.currentThread.getName }.distinct
//    val result2 = (0 to 10000000).par.map { case _ => Thread.currentThread.getName }.distinct
//    println(result1)
//    println(result2)  //12线程
  }
}
