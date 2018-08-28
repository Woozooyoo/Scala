package unit6

object Match_test {
  def main(args: Array[String]): Unit = {
    //switch
    def matchCase() = {
      var result = 0
      val c = "*"
      c match {
        case "+" => result = 1
        case _ => result = -1
      }
      println(result)
    }

    matchCase()

    //守卫
    def matchGuard() = {
      for (c <- "1!2@3#4$5%6^7&8abcABC|") {
        c match {
          case variable if Character.isDigit(c)=>print("digital ")
          case _ => print(c.toByte+" ")
        }
      }
    }
    matchGuard();println(122.toChar)

    def matchClass()={
      val a = 4 //4 和5的结果都是 第一个匹配的Map
      val obj = if (a == 1) 1
      else if (a == 2) "2"
      else if (a == 3) BigInt(3)
      else if (a == 4) Map("aa" -> 1)
      else if (a == 5) Map(1 -> "aa")
      else if (a == 6) Array(1, 2, 3) //数组有类型 Int[]
      else if (a == 7) Array("aa", 1)
      else if (a == 8) Array("aa")
    }

  }
}
