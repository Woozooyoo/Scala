package unit6

object Match {
  def main(args: Array[String]): Unit = {
    //1、match 中的 switch用法
    def match1() = {
      var result = 0
      val c: Char = '*'

      c match {
        case '+' => result = 1
        case '-' => result = -1
        case _ => result = 0
      }
      println(result)
    }

    match1()
    println("******上面是1、match 中的 switch用法:变量c match{case _ =>执行语句")

    //2、匹配模式——守卫
    def match2() = {
      for (c <- "+-*/123!") {
        c match {
          //将c传进 value变量
          case value /*_*/ if Character.isDigit(c) => print("这是一个数字:" + value + " ")
          case '+' => print("这次字符为+号 ")
          case '-' => print("这次字符为-号 ")
          case '*' => print("这次字符为*号 ")
          case '/' => print("这次字符为/号 ")
          case _ => print("这次字符为通配 ") //没有处理则出错
        }
      }
    }

    match2()
    println("\n******上面是2、守卫 boolean语句和3、模式中的变量 把变量c传给value")

    //4、类型匹配
    def match3() = {
      val a = 4 //4 和5的结果都是 第一个匹配的Map
      val obj = if (a == 1) 1
      else if (a == 2) "2"
      else if (a == 3) BigInt(3)
      else if (a == 4) Map("aa" -> 1)
      else if (a == 5) Map(1 -> "aa")
      else if (a == 6) Array(1, 2, 3) //数组有类型 Int[]
      else if (a == 7) Array("aa", 1)
      else if (a == 8) Array("aa")

      val r1 = obj match {
        case i: Int => i
        case s: String => s
        case bi: BigInt => bi
        case m2: Map[Int, String] => print("这是Map[Int, String] ")
        case m1: Map[String, Int] => print("这是Map[String, Int] ")
        case a1: Array[Int] => print("Array[Int] ")
        case a3: Array[String] => print("Array[String] ")
        case a2: Array[Any] => print("Array[Any] ")
      }
      println("\n" + r1 + "：" + r1.getClass.getName)
    }

    match3()
    println("******上面是4、类型匹配")

    //5、匹配数组
    def match4() = {
      for (arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(1, 1, 1, 0))) {
        arr match {
          case Array(0) => print("匹配了Array(0) ")
          case Array(x, y) => print("匹配了Array(x, y):" + x + "," + y + " ")
          case Array(x, y, z) => print("匹配了Array(x, y, z):" + x + "," + y + "," + z + " ")
          case Array(1, arr@_* /*除了1剩下的为数组*/) => print("匹配了 Array(1, _*):" + arr.length + " ")
          case _ => print("通配 ")
        }
      }
      println()

      for (lst <- Array(List(0), List(1, 0), List(0, 0, 0), List(1, 0, 0))) {
        val result = lst match {
          case 0 :: Nil => "0"
          case x :: y :: Nil => x + "," + y
          case 0 :: tail => "0..."
          case _ => "something else"
        }
        print(result + "  ")
      }
      println()

      for (pair <- Array((0, 1), (1, 0), (1, 1))) {
        val result = pair match {
          case (0, _) => "0..."
          case (y, 0) => y + ",0"
          case _ => "neither is 0"
        }
        print(result + "  ")
      }

    }

    match4()
    println("\n******上面是5、匹配数组、列表、元祖")

    //6、匹配模式中的提取机制
    object Square {
      def unapply(z: Double): Option[Double] = Some(math.sqrt(z))
    }

    def match5() = {
      val number = 36.0
      number match {
        //调用了Square的 unapply(n) 返回 Some(res)
        case Square(n) => println(s"square root of $number is $n")
        case _ => println("nothing matched")
      }
    }

    match5()
    println("******上面是6、匹配的提取机制  **不懂")


    val (x, y) = (1, 2)
    val (q, r) = BigInt(10) /% 3
    val arr = Array(1, 7, 2, 9)
    val Array(first, second, _*) = arr

  }
}
