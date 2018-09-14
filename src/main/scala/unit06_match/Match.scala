package unit06_match

import scala.collection.mutable
//.par是括号快捷键
//.match是快捷键
// 常量判断 变量赋值 下划线通吃
object /*伴生类对象，不用构造器*/ Match {
  def main(args: Array[String]): Unit = {
    println("/************** 1、A Better Switch: n match{case '?'=> *********************************/")
    def matchCase() = {
      var result = 0
      val c: Char = '*' //.match是快捷键
      c match {
        case '+' => result = 1
        case '-' => result = -1
        case _ => result = 0
      }
      println(result)
    }

    matchCase()

    println("/************** 2、Guards  3、Variables in Patterns 把变量c传给value*********************************/")
    def matchGuard() = {
      for (c <- "+-*/123!") {
        c match {
          //将c传进 value变量
          case value /*_*/ if Character.isDigit(c) => print("数字" + value + " ")
          case '+' => print("+号 ")
          case '-' => print("-号 ")
          case '*' => print("*号 ")
          case '/' => print("/号 ")
          case _ => print("通配 ") //没有处理则出错
        }
      }
    }

    matchGuard()
    println()

    println("/************** 4、Type Patterns 有泛型擦除 case i:类型 => *********************************/")
    def matchClass() = {
      for (arr <- Array(
        1,
        "2",
        BigInt(3),
        Map("aa" -> 1),
        Map(1 -> "aa"),
        Array(1, 2, 3),
        Array("aa", 1),
        Array("aa")) ) {

        arr match {
          case i: Int => print("Int ")
          case s: String => print("String ")
          case bi: BigInt => print("BigInt ")
          case m2: Map[Int, String] => print("Map[Int, String] ")
          case m1: Map[String, Int] => print("Map[String, Int] ")
          case a1: Array[Int] => print("Array[Int] ")
          case a3: Array[String] => print("Array[String] ")
          case a2: Array[Any] => print("Array[Any] ")
        }
      }
    }

    matchClass()

    def match5() = {
      println("/************** 5、Matching Arrays *********************************/")
      for (arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(1, 1, 1, 0), Array(0, 0, 0, 0))) {
        arr match {
          case Array(0) => print("Array(0) ")
          case Array(x, y) => print("Array(x, y):" + x + "," + y + " ")
          case Array(x, y, z) => print("Array(x, y, z):" + x + "," + y + "," + z + " ")
          case Array(1, arr1@_* /*除了1剩下的为数组*/) => print("Array(1, _*):" + arr1.length + " ")
          case Array(0, _*) => print("Array(0,_*):" + arr.length+ " ")
          case _ => print("通配 ")
        }
      }
      println()

      println("/************** 5、Matching Lists *********************************/")
      for (lst <- Array(List(0), List(1, 0), List(0, 0, 0), List(1, 0, 0))) {
        val result = lst match {
          case 0 :: Nil => "List(0)"
          case x :: y :: Nil => x + "," + y
          case 0 :: aaa => "0..." + aaa
          case _ => "something else"
        }
        print(result + "  ")
      }
      println()

      println("/************** 5、Matching Tuples *********************************/")
      for (pair <- Array((0, 1), (1, 0), (1, 1), (0, 0, 0))) {
        val result = pair match {
          case (0, _) => "0,_"
          case (y, 0) => y + ",0"
          case (0, _, _) => "0,_,_"
          case _ => "neither is 0"
        }
        print(result + "  ")
      }

    }

    match5()
    println()

    println("/************** 6、Extractors机制 unapply *********************************/")
    object Square {
      def unapply(z: Double): Option[Double] = Some(math.sqrt(z))
    }
    /*--调用了Square的 unapply，传入要match的num
      --接收返回值并判断返回值是None，还是Some
      --如果是Some，则将其解开，并将Some其中的值(math.sqrt(num))赋值给res
      （就是case Square(res)中的res）
      --如果是None，则走 case _的方法*/
    def matchExtract() = {
      val num = 36.0
      num match {
        case Square(res) => println(s"square root of $num is $res")
        case _ => println("nothing matched")
      }
    }

    matchExtract()

    object /*伴生类对象，不用构造器*/ Names {
      def unapplySeq(str: String): Option[Seq[String]] = {
        if (str.contains(",")) Some(str.split(","))
        else None
      }
    }
    /*--调用unapplySeq，传入要match的namesString
      --接收返回值并判断返回值是None，还是Some
      --如果是Some，则将其解开
      --判断解开之后得到的Seq中的元素的个数是否是三个
      --如果是三个，则把三个元素分别取出，赋值给first，second和third*/
    def matchExtractSeq() = {
      val namesString = "Alice,Bob,Thomas"
      namesString match {
        case Names(first, second, third) => {
          println("the string contains three people's names")
          println(s"$first $second $third")
        }
        case _ => println("nothing matched")
      }
    }

    matchExtractSeq()

    println("/************** 7、Patterns in Variable Declarations，match中每一个case都可以单独提取出来 *********************************/")
    val (x, y) = (1, 2)
    println(1, 2)
    println(x, y)
    val (q, r) = BigInt(100) /% 3 //33 1
    println(BigInt(100) /% 3)
    println(q, r)
    val arr = Array(1, 7, 2, 9)
    val Array(first, second, _*) = arr
    println(arr.mkString(" "))
    println(first, second)
    val Array(fi, s, vec@_* /*将剩下的元素赋值给vec变量*/) = arr
    println(fi, s, vec)
    val list=1::3::4::5::Nil
    val one :: two :: rest=list
    println(one+","+two+","+rest)

    println("/************** 8 Patterns in for Expressions *********************************/")
    import scala.collection.JavaConverters._
    //    for ((k, v) <- System.getProperties.asScala)
    //      println(k + " -> " + v)
    //有k 但v是空的  for中匹配会自动忽略失败的匹配
    for ((k, "") <- System.getProperties.asScala) //Same as below
      println(k)

    println("-------")
    for ((k, v) <- System.getProperties.asScala if v == "")
      println(k, v)

    println("/************** 9 Case Classes *********************************/")
    abstract class Amount
    //样例类它是为模式匹配而优化的类，样例类用case关键字进行声明
    //会自动创建apply和unapply方法，参数一样 自动创建toString copy
    case class Dollar(value: Double) extends Amount
    //    def unapply(value: Double): Option[Double] = Some(value)
    case class Currency(value: Double, unit: String) extends Amount
    case object Nothing /*伴生类对象，不用构造器*/ extends Amount

    def matchCaseClass() = {
      for (e <- Array(Dollar(1000.0), Currency(1000.0, "EUR"), Nothing)) {
        val result = e match {
          case Dollar(v) => "$" + v
          case Currency(_, u) => u
          case Nothing => ""
        }
        println(e + ": " + result)
      }
    }

    matchCaseClass()

//    练习：
    case class Emp(name: String, salary: Double)
    val emps=List(Emp("zhangchen",10000),Emp("guodai",20000),Emp("zhang3",22000),Emp("wang4",34444),Emp("li4",12222))
    //以10000为一个档次  列出个各个薪酬档次的员工个数
    //放到Map[String,Int]中
    val resultMap = mutable.Map[String, Int]()
    for (e <- emps) {
      e match {
        case Emp(_, a1) if a1 >= 0 & a1 < 10000 => resultMap += ("0到10000" -> (resultMap.getOrElse("0到10000", 0) + 1))
        case Emp(_, a1) if a1 >= 10000 & a1 < 20000 => resultMap += ("10000到20000" -> (resultMap.getOrElse("10000到20000", 0) + 1))
        case Emp(_, a1) if a1 >= 20000 & a1 < 30000 => resultMap += ("20000到30000" -> (resultMap.getOrElse("20000到30000", 0) + 1))
        case Emp(_, a1) if a1 >= 30000 & a1 < 40000 => resultMap += ("30000到40000" -> (resultMap.getOrElse("30000到40000", 0) + 1))
        case _ => println("other")
      }
      println(e + ": " + resultMap)
    }
    println(resultMap)

    println("/************** 10 Copy Method and Named Parameters *********************************/")
    val amt = Currency(29.95, "EUR")
    val charge = amt.copy(value = 19.95)
    println(amt)
    println(charge)
    println(amt.copy(unit = "CHF"))

    println("/************** 11 Infix Notation in case Clauses *********************************/")
    List(1, 2, 3, 4) match {
      case first :: second :: rest => println(first + "," + second + "," + rest)
      case _ => 0
    }

    println("/************** 12  Matching Nested Structures  比如某一系列商品想捆绑打折出售 *********************************/")
    //  创建样例类
    abstract class Item //促销项目
    case class Article(description: String, price: Double) extends Item
    case class Bundle(description: String, discount: Double /*打折金额*/ , item: Item*) extends Item

    //  匹配嵌套结构
    val sale =
      Bundle("愚人节大甩卖系列", 10,
        Article("《九阴真经》", 40),
        Bundle("从出门一条狗到装备全发光的修炼之路系列", 20,
          Article("《如何快速捡起地上的装备》", 80),
          Article("《名字起得太长躲在树后容易被地方发现》", 30)))

    //1)  将descr绑定到第一个Article的描述
    val result1 = sale match {
      case Bundle(_, _, Article(descr, _), _*) => descr
    }
    println(result1)

    //2)  通过@表示法将嵌套的值绑定到变量。_*绑定剩余Item到rest
    val result2 = sale match {
      case Bundle(_, _, art@Article(_, _), rest@_*) => (art, rest)
    }
    println(result2)

    //3)  不使用_*绑定剩余Item到rest
    val result3 = sale match {
      case Bundle(_, _, art@Article(_, _), rest) => (art, rest)
    }
    println(result3)

    //4)  计算某个Item价格的函数，用了price函数的递归调用
    def price(it: Item): Double = {
      it match {
        case Article(_, p) => p
        case Bundle(_, disc, its@_*) => its.map(price _).sum - disc
      }
    }

    println(price(sale))
    //第一层 price _, 10disc1, its={A(_,40),B[_,20,a1(_,80),a2(_,30)]}
    //map   price A=>40
    //      price B=>_, 20disc2, its={a1(_,80),a2(_,30)}
    //                          map price a1=>80
    //                              price a2=>30
    //                               sum 80+30 =110  -20disc2 = 90
    //      40+90=130 -10disc1 =120

    println("/************** 13  Sealed Classes 14 Simulating Enumerations*********************************/")
    //1)  创建密封样例类（不密封也可以
    sealed abstract class TrafficLightColor
    //密封类的子类只能在同一个文件中创建
    case object /*伴生类对象，不用构造器*/ Red extends TrafficLightColor
    case object Yellow extends TrafficLightColor
    case object Green extends TrafficLightColor

    println(Red.getClass.getName)
    //2)  模拟枚举
    for (color <- Array(Red, Yellow, Green)) {
      println(
        color match {
          case Red => "stop"
          case Yellow => "slowly"
          case Green => "go"
        })
    }

    println("/************** 15  Partial Functions偏函数  PartialFunction的匿名子类*********************************/")
    //偏函数，只有能力处理case的条件，超出case的条件没能力处理，
    //它只对会作用于指定类型的参数或指定范围值的参数实施计算,如果函数体里只有模式匹配就是偏函数
    val f: PartialFunction[Char /*传的参数*/ , Int /*结果*/ ] = {
      case '+' => 1
      case '-' => -1
      case _ => 10
    }
    println(f('-'))
    println(f.isDefinedAt('0')) //f是否对传入参数 0 的情况定义
    println(f('+'))
    println(f(0))

    //我们定义一个将List集合里面数据+1的偏函数：
    val f1 = new PartialFunction[Any, Int] {
      def apply(any: Any) = any.asInstanceOf[Int] + 1

      def isDefinedAt(any: Any) = if (any.isInstanceOf[Int]) true else false
    } //collect use isDefinedAt first,then use apply
    val rf1 = List(1, 3, 5, "7", "seven") collect f1
    println(rf1)

    //----------------------------f2与f1功能一样的------------------------------
    def f2: PartialFunction[Any, Int] = {
      case i: Int => i + 1
    } //default isDefinedAt apply
    val rf2 = List(1, 3, 5, "7", "seven") collect f2
    println(rf2)

    //    println(List(1, 3, 5, "seven") map { case i: Int => i + 1 }) // won't work
    // scala.MatchError: seven (of class java.lang.String)
    println(List(1, 3, 5, "7", "seven") collect { case i: Int => i + 1 })
    // verify If not=》java.lang.AssertionError: assertion failed
    assert(List(2, 4, 6) == (List(1, 3, 5, "seven") collect { case i: Int => i + 1 }))

  }
/**for循环中遍历Map集合,可以把Map集合中元素遍历到元祖使用的是匹配模式*/
}
