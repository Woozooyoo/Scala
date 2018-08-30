package unit4_function

/**
  * 懒值  真正使用时才执行代码块里的
  */
object Lazy {
  def main(args: Array[String]): Unit = {
    def play1(a1: Int) = {
      println("play1方法被执行")
      a1
    }

    lazy/*懒值*/ val l1 = play1(10)
    println("lazy变量定义完毕")
    println(l1)
  }
}
