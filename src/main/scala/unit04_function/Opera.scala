package unit4

/** itr */
object Opera {
  def main(args: Array[String]): Unit = {
    //1、if else
    var a = 10
    var resultString = ""
    val desc = if (a > 10) {
      "a大于等于10"
    } else if (a == 10) {
      resultString = "a等于10"
    } else {
      "else"
    }
    println("desc的值是：" + desc)
    println("resultString的值是：" + resultString)

    //2、while...do...while
    val resultWhile = while (a < 11) {
      println("a的值是：" + a)
      a += 1
    }
    // while的返回值为Unit
    println("resultWhile的值是：" + resultWhile)

    //    do {
    //    } while (true)

    //3、循环的终止
    import scala.util.control.Breaks
    val looper = new Breaks
    //////
    var count = 0
    looper breakable {
      while (count <= 100) {
        count += 1
        print(count + " ")
        if (count == 10)
          looper break
      }
    }
    println()
    /*
        val whileLoopter1 = new Breaks
        val whileLoopter2 = new Breaks

        whileLoopter1.breakable{
          while(true) {
            count+=1
            if(count==11){
              whileLoopter1 break()
            }
            whileLoopter2 breakable{
              while (true) {
                if(count==20){
                  whileLoopter2 break()
                }
              }
            }
          }
        }*/

    //4、for循环  to是前闭后闭 3层for循环嵌套
    for (i <- 1 to 3; j <- 1 to 3; m <- 1 to 3) {
      print(i + "-" + j + "-" + m + " ")
    }
    println()

    //5、想输出10 9 8 ....1   前闭后开
    for (i <- Range(10, 0, -1)) {
      print(i + " ")
    }
    println()

    for (i <- (1 to(20, 2)).reverse) {
      print(i + " ")
    }
    println()

    //6、输出1~9   until 前闭后开
    for (i <- 1 until 10) {
      print(i + " ")
    }
    println()

    //7、实现1~10的输出，只输出偶数 for循环的守卫 不满足的continue：跳过到下一个
    for (i <- 1 to 10 if i % 2 == 0) {
      print(i + " ")
    }
    println()

    //8、遍历的时候，把遍历的所有的结果，保存到一个集合中输出 集合能打印 Array不能打印，
    val forResult = for (i <- 1 to 10) yield i * 2
    println(forResult)

    //9、在for循环中，添加变量表达式
    for (i <- 1 to 3; j = 4 - i) {
      print(j + " ")
    }
    println()

    //10、在for循环中，添加变量表达式 i执行一次 j和m执行一次
    for (i <- 1 to 10; j = 10 - i; m = j * i) {
      print("j:" + j + " ")
      print("m:" + m + " ")
    }
    println()

    //11、使用花括号作为for循环条件包裹
    for {
      i <- 1 to 3
      m = i * 2
    }
      print(m + " ")
    println()

    //12、for循环打印实心矩形
    for (i <- 1 to 2; j <- 1 to 10) {
      print("*")
      if (j == 10)
        println()
    }

    //13、找出1~100的所有素数
    var flag = true
    for (i <- 2 to 100; m <- 2 until i) {
      if (i % m == 0)
        flag = false
      if (m == i - 1) {
        if (flag)
          print(i + " ")
        flag = true
      }
    }
    println()

    println(for (i <- 2 to 100; m <- 2 until i; if i % m != 0 && m == i - 1) yield i)

    //14、使用守卫实现 continue功能 （遍历自然数集合，不取其中的奇数）
    println(for (i <- 1 to 100 if i % 2 != 0) yield i)

    //九九乘法表
    for (i <- 1 to 9; j <- 1 to i) {
      if (i == j)
        print(j + "x" + i + "=" + (i * j) + "\n")
      else
        print(j + "x" + i + "=" + (i * j) + "\t")
    }
  }
}
