package unit6_match

object Match_test {
  def main(args: Array[String]): Unit = {
    //switch
    def matchCase() = {
      var res = "0"
      val c = "&"

      c match {
        case ")" => res = ")"
        case "(" => res = "("
        case "*" => res = "*"
        case "&" => res = "&"
        case _ => res = "_"
      }
      println(res)
    }

    matchCase()

    //守卫
    def matchGuard() = {
      for (c <- "1!2@3#4$5%6^7&8abcABC|") {
        c match {
          case variable if Character.isDigit(variable) => print("digital ")
          case _ => print(c.toByte + " ")
        }
      }
    }

    matchGuard();
    println(122.toChar)

    def matchClass() = {
      val a = 5 //4 和5的结果都是 第一个匹配的Map
      val obj = if (a == 1) 1
      else if (a == 2) "2"
      else if (a == 3) BigInt(3)
      else if (a == 4) Map("aa" -> 1, "bb" -> 2)
      else if (a == 5) Map(1 -> "aa", 2 -> "bb")
      else if (a == 6) Array(1, 2, 3) //数组有类型 Int[]
      else if (a == 7) Array("aa", 1)
      else if (a == 8) Array("aa")

      val r1 = obj match {
        case i: Int => i
        case s: String => s
        case b: BigInt => b
        case m2: Map[Int, String] => print("这是Map[Int, String] ")
        case m1: Map[String, Int] => print("这是Map[String, Int] ")
        case a1: Array[Int] => print("Array[Int] ")
        case a3: Array[String] => print("Array[String] ")
        case a2: Array[Any] => print("Array[Any] ")
      }
      println("\n" + r1 + ":" + r1.getClass)
    }

    matchClass()

    object Square {
      def unapply(arg: Int): Some[Double] = Some(math.sqrt(arg))
    }

    def matchExtractUnapply() = {
      val num = 49
      num match {
        case Square(res) => println(s"square root of $num is $res")
        case _ => println("nothing matched")
      }
    }

    matchExtractUnapply()

    object Names /*没有括号*/ {
      def unapplySeq(str: String): Option[Seq[String]] = {
        if (str.contains(",")) Some(str.split(","))
        else None
      }
    }

    def matchExtractUnapplySeq() = {
      val namesString = "Alice,Bob,Thomas,Charles"
      namesString match {
        case Names(f, s, t, q) => {
          println(s"$f,$s,$t,$q")
        }
        case _ => println("nothing")
      }
    }

    matchExtractUnapplySeq()

    val (x, y) = (1, 2)
    val (q, r) = BigInt(100) /% 3
    println(BigInt(100) /% 3)
    val arr = Array(1, 2, 3, 4)
    val Array(f, s, t, xx@_*) = arr
    println(f, s, t, xx)

    import scala.collection.JavaConverters._
    for ((k, "") <- System.getProperties.asScala) //Same as below
      println(k)
    for ((k, v) <- System.getProperties.asScala if v == "")
      println(k)
  }
}
