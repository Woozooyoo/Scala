package unit05_collection

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

/**
  * 集合三要素
  * 1定义
  * 2赋值 取值
  * 3追加
  * 4（遍历）
  */
object Collection {
  def main(args: Array[String]): Unit = {
    println("============== 1、定长数组Array =================================/")
    val arr1 = new Array[Int](10)
    println(arr1 mkString " ") //0 0 0 0 0 0 0 0 0 0

    val arr2 = Array(1, 2, 3)
    println(arr2 mkString " ") //1 2 3
    //取值
    println(arr2(1)) //2
    println(arr2.sum) //6
    //赋值  定长数组里面的值可改变
    arr2(1) = 10 //arr2 update(1,10)
    println(arr2 mkString " ") //1 10 3

    //Array :+ 追加元素  产生新的集合对象 如果追加数组 则变成追加数组地址值
    println((arr2 :+ "???") mkString " ") //??? 1 10 3
    println(("???" +: arr2) mkString " ") //??? 1 10 3

    println("==============  变长数组ArrayBuffer 等与java的ArrayList 数组结构 有下标索引快 追加元素会扩容=================================/")
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
    println(arrBuffer1 :+ 99) //ArrayBuffer(10, -1, 8, 1, 2, 3, 99)
    println(99 +: arrBuffer1) //ArrayBuffer(99, 10, -1, 8, 1, 2, 3)

    println("============== 定义多维数组 =================================/")
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

    println("============== 2 scala和java的互相转换定 =================================/")
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

    println("============== 3 元组 =================================/")
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

    println("\n============== 4、List 定长 =================================/")
    val list1 = List(1, 2)
    //取值
    println("list1(1): " + list1(1))
    //特殊：定长List只有 updated赋值成新的list1_new
    val list1_new = list1 updated(1, 10)
    println("list1: " + list1)
    println("list1_new: " + list1_new)
    //追加 成新的List :: 从右往左创建  ::是追加 把元素往最右边起始的集合括号里添加
    println("Nil: " + Nil)
    val list2_1 = 1 :: 2 :: 3 :: Nil
    println("list2_1: " + list2_1)
    val list2_2 = 1 :: 2 :: 3 :: List(6, 7) :: Nil
    println("list2_2: " + list2_2)
    val list2_3 = 1 :: 2 :: 3 :: List(6, 7)
    println("list2_3: " + list2_3)
    //列表拼接  Array和Set也行
    println(List(6, 7) ++ List(5, 9))

    println("============== 变长 ListBuffer 相当于LinkedList 链表结构 插入快toList速度更快 按下标取值慢=================================/")
    val listBuffer = ListBuffer[Int](3, 0)
    //append追加在原有的ListBuffer里追加，不形成新的集合
    listBuffer append(1, 2)
    println(listBuffer)
    // :+ 追加形成新的集合 区别于append
    println(listBuffer :+ 3)
    //ListBuffer update 和List 的updated不同
    listBuffer update(0, 10)
    println(listBuffer)

    println("============== 5、队列 =================================/")
    val q1 = scala.collection.mutable.Queue[Int](1, 2)
    println(q1)
    //赋值
    q1(1) = 10
    println(q1)
    //追加元素
    q1 += 20
    println(q1)
    val q2 = new mutable.Queue[Int]()
    // ++=追加集合
    q2 ++= q1
    println("q2: " + q2)
    q1 ++= List(1, 2, 3)
    println(q1)

    //等同于 +=
    q1 enqueue(99, 98)
    println(q1)

    //删除 dequeue 先进先出，删除第一个
    q1 dequeue()
    println(q1)

    println(q1 head)
    println(q1 last)
    println(q1 tail) //the queue except the first one    expect

    println("============== 5、栈 =================================/")
    //Stack:FILO
    import scala.collection.mutable.Stack
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.push(3)
    println(stack.pop())
    println(stack.top)
    println(stack)
    println(stack.pop())
    println(stack)
    println("============== 6、Map key 和value的类型都无所谓 =================================/")
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

    //option  是一个集合
    val map = Map("name" -> "Lion", "age" -> 22)
    println("\n" + map("age") + "22")
    println(map.get("name"))
    println(map.get("sex"))
    println(map.getOrElse("sex", 23))
    println(map.head)

    println("============== 7、Set =================================/")
    val set1 = Set(1, 2, 2, 3) //去重
    println(set1)
    val set2 = Set(4, 2, 2, 8)
    println(set1 & set2) //交 并 差
    println(set1 ++ set2)
    println(set1 &~ set2)
    println(Set(10, 1, 3).+(9) &~ Set(4) & Set(10))
    val mutableSet = mutable.Set(1, 2, 2, 3, "3") //会出现3和"3"
    println(mutableSet)
    //没有取值 只有判断值是否存在
    println(set1(0)) //false //等同于contains(value)
    //Set只有追加 删除 value

    //add追加 返回的是追加是否成功
    println(mutableSet add 4)
    println(mutableSet)
    //等同于add 但是返回的是添加后的Set
    println(mutableSet += 5)
    mutableSet -= 1
    mutableSet remove 4 //等同与-=
    println(mutableSet)

    //遍历
    for (s <- mutableSet)
      print(s + " ")

    println("\n============== 8、List集合中元素与函数之间的映射 =================================/")
    val listOri = List("Alice", "Bob", "Kotlin")

    //map的作用是对集合中的每个元素 映射 一个方法
    def f1(x: String): String = x.toLowerCase()

    println(listOri.map(f1))
    //省略函数名 匿名函数  也省略了返回类型
    println(listOri.map((x: String) => x toLowerCase))
    //省略参数类型
    println(listOri.map(x => x toUpperCase))

    //"_"代表每一个元素，这个元素只能被使用一次
    println(listOri.map(_.toLowerCase)) //List(alice, bob, kotlin)
    //每一个元素设为变量x 可以使用多次
    println(listOri.map(x => x.toUpperCase + x.toLowerCase)) //List(ALICEalice, BOBbob, KOTLINkotlin)

    //flatMap 压散压碎Map
    //    println(listOri flatMap (x=>x.toUpperCase+x.toLowerCase)) //与下同
    //List(A, L, I, C, E, a, l, i, c, e, B, O, B, b, o, b, K, O, T, L, I, N, k, o, t, l, i, n)
    println(listOri flatMap (x => x.toUpperCase() + x.toLowerCase()))

    println(listOri.flatMap(_.toUpperCase())) //List(A, L, I, C, E, B, O, B, K, O, T, L, I, N)

    println("============== 9 化简 折叠 扫描 =================================/")
    println("======================reduce方法，就是传0初始值的fold方法======================")
    //Reduce
    val listReduced = List(1, 2, 3)
    //1-2) -3)
    println(listReduced.reduceLeft(_ - _))
    println(listReduced.reduceLeft((r, x) => r - x))
    //(1- (2-3
    println(listReduced.reduceRight(_ - _))
    println(listReduced.reduceRight((r, x) => r - x))

    println("======================fold方法======================")
    //Fold
    val listFolded = List(1, 2, 3)
    //fold=foldLeft   5 是初始值  foldLeft就是以一个初始值5 为最左边的数
    //                                    从左到右 进行函数
    println(listFolded.fold(5)((res, y) => res - y)) //5-1 -2 -3  = -1
    println((5 /: listFolded) (_ - _))
    println((5 /: listFolded) ((res, y) => res - y)) //5-1 -2 -3  = -1

    //(1- (2- (3-5=-3  以下三个结果相同 foldRight就是以一个初始值为右边的数
    //                                    从右到左 进行函数
    println(listFolded.foldRight(5)((result, y) => result - y))
    //foldRight
    println(listFolded.foldRight(5)(_ - _))
    println((listFolded :\ 5) (_ - _))

    import mutable.Set
    def f10(i: Int, s: Set[Int]): Set[Int] = {
      s += s.size + i
    }

    println("面试题: " + (List(1, 2, 3, 4) :\ Set(2)) (f10))

    def f11(i: Int, s: Set[Int]): Set[Int] = {
      (s += s.size) + i
    }

    println("面试题: " + (List(1, 2, 3, 4) :\ Set(2)) (f11))

    println(Map[Char, Int]())
    //统计一句话中，各个文字出现的次数
    val sentence = "一首现代诗《笑里藏刀》:哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈刀哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
    //m +  (“一” -> 1, “首” -> 1, “哈” -> 1)
    //初始值是Map() foldLeft sentence
    //((m是上一步的结果Map(), c是下一个元素"一") => m是Map() + 映射关系(c "一" -> (m.getOrElse(c, 0)有就得到次数，else没有就默认给(c,0)的映射 + 1)))
    val mapCount = (Map[Char, Int]() /: sentence) ((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
    println(mapCount)

    def charCount(map:Map[Char, Int],char:Char):Map[Char, Int]={
      map  + (char -> (map.getOrElse(char, 0) + 1))
    }
    val mapCount1 = sentence.toCharArray.foldLeft (Map[Char, Int]()) (charCount)
    println(mapCount1)

    val mapCount2 = sentence.toCharArray.foldLeft (Map[Char, Int]()) ((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
    println(mapCount2)
    println(sentence.groupBy(s=>"\""+s+"\""))
    println(sentence.groupBy(s=>"\""+s+"\"").map(t=>(t._1,t._2.length)))

    println("======================scan方法======================")
    //    这个理解需要结合上面的知识点，扫描，即对某个集合的所有元素做fold操作，但是会把产生的所有中间结果放置于一个集合中保存。
    println(listReduced.scanLeft(0)(_ + _))
    println(listReduced.scan(5)(_ - _)) //List(5, 4, 2, -1)
    println(listReduced.scanRight(5)(_ - _)) //List(-3, 4, -2, 5)

    println("======================Curry化======================")
    //Curry //调用第一个匿名函数，它的变量是第二个匿名函数
    val myFunction = (x: Int) => (y: Int) => x - y
    println(myFunction(10))
    println(myFunction(10)(20))

    //Curry化
    def myFunctionCurry(x: Double)(y: Double) = x / y

    println(myFunctionCurry(10)(20))


    println("============== 10、拉链 就是融合两个List的方法=================================/")
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

    println("============== 12、stream 用到多大的区间，就会动态的生产，使用后才缓存 末尾元素遵循lazy规则。 =================================/")

    def recursiveList(n: Int): List[Int] = {
      n :: recursiveList(n + 1)
    }
    //    println(recursiveList(10))  //StackOverflowError List会出错

    def recursiveStream(n: Int): Stream[Int] = {
      n #:: recursiveStream(n + 1)
    }

    val numStream = recursiveStream(0)
    //tail 从初始状态开始 进行一个方法(+1) 并排除第一个返回
    println(numStream) //Stream(0, ?)
    println(numStream.tail) //Stream(1, ?)
    println(numStream) //Stream(0, 1，?)
    println(numStream.tail.tail.tail) //Stream(3, ?)  从0开始 一直排除第一个
    println(numStream) //Stream(0, 1, 2, 3, ?) 打印出最新的状态
    println(numStream.tail.tail.tail.tail) //Stream(4, ?)
    println(numStream) //Stream(0, 1, 2, 3, 4, ?)
    println(numStream.tail) //Stream(1, 2, 3, 4, ?)
    println(numStream) //Stream(0, 1, 2, 3, 4, ?)
    println(numStream.tail.tail) //Stream(2, 3, 4, ?)
    println(numStream) //Stream(0, 1, 2, 3, 4, ?)

    val numStream1 = recursiveStream(0)
    val s1 = numStream1.tail
    println(s1) //Stream(1, ?)      两个s1输出不同
    val s2 = s1.tail
    println(s1) //Stream(1, 2, ?)   两个s1输出不同
    println(s2) //Stream(2, ?)
    println(numStream1) //Stream(0, 1, 2, ?)

    def randomNum():Stream[Int]={
      Random.nextInt(100000)#::randomNum()
    }
//    def isMo
    //    randomNum().filter(num=> )

    def Recursive(n: Any): Stream[Any] = {
      n #:: Recursive("Sofia")
    }

    val stream = Recursive("AlvaroSoler")
    println(stream) //Stream(AlvaroSoler, ?)
    println(stream.tail) //Stream(Sofia, ?)
    println(stream) //Stream(AlvaroSoler, Sofia, ?)
    println(stream.tail.tail) //Stream(Sofia, ?)
    println(stream) //Stream(AlvaroSoler, Sofia, Sofia, ?)
    println(stream.tail.tail.tail) //Stream(Sofia, ?)
    println(stream) //Stream(AlvaroSoler, Sofia, Sofia, Sofia, ?)
    println(stream.tail) //Stream(Sofia, Sofia, Sofia, ?)
    println(stream.tail.tail) //Stream(Sofia, Sofia, ?)

    //13、view 使用完后不缓存 只保留函数 每次调用时才加载
    //    val view = (1L to 10000000L).view.map(x => x).filter(x => x.toString.reverse == x.toString)
    //    println(view) //SeqViewMF(...)
    //    println(view.mkString(" "))
    //    println(view.mkString(" "))

    //    val result1 = (0 to 10000000).map { case _ => Thread.currentThread.getName }.distinct
    //    val result2 = (0 to 10000000).par.map { case _ => Thread.currentThread.getName }.distinct
    //    println(result1)  //main线程
    //    println(result2)  //12线程
    (1 to 5).par.foreach(print(_))  //parallel
  }
}
