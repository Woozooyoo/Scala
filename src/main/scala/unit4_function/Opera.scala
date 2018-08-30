package unit4_function

object Opera {
  def main(args: Array[String]): Unit = {
    var a = 10
    var resultString = ""
    val desc = if (a > 10) {
      "a>10"
    } else if (a == 10) {
      resultString = "a==10"
    } else {
      a < 9
    }
    println(desc)
    println(resultString)

    val resultWhile = while (a < 12) {
      println(a)
      a += 1
    }
    println(resultWhile)

    import scala.util.control.Breaks
    val looper = new Breaks
    var count = 0
    looper breakable {
      while (count <= 100) {
        count += 1
        print(count + " ")
        if (count == 10)
          looper break()
      }
    }
    println()

    /** for */
    for (i <- 1 to 3; j <- 1 to 3; m <- 1 to 2) {
      print(i + "-" + j + "-" + m + " ")
    }
    println()

    for (i <- Range(10, 0, -1)) {
      print(i + " ")
    }
    println()

    for (i <- 1 until (10))
      print(i + " ")
    println()

    for (i <- 1 to 10 if i % 2 == 0) print(i + " ")
    println()

    println(for (i <- 1 to 10) yield i * 2 + 1)

    for (i <- 1 to 3; j = 4 - i) {
      print(j + " ")
    }
    println()

    for (i <- 1 to 10; j = 10 - i; m = j * i) {
      print("j:" + j + " ")
      print("m:" + m + " ")
    }
    println()

    for {
      i <- 1 to 3
      m = i * 2 + 1
    }
      print(m + " ")
    println()

    for (i <- 1 to 9; j <- 1 to i; m = i * j) {
      if (i == j)
        print(i + "x" + j + "=" + m + "\n")
      else
        print(i + "x" + j + "=" + m + "\t")
    }

    var flag = true
    for (i <- 2 to 50; m <- 2 until i) {
      if (i % m == 0)
        flag = false
      if (m == i - 1) {
        if (flag) {
          print(i + " ")
        }
        flag = true
      }
    }
    println()

    print(for (i <- 1 to 50 if i % 2== 0) yield i)
  }
}
