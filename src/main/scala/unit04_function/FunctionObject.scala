package unit04_function

object FunctionObject {
  def main(args: Array[String]): Unit = {
    //最完整形式
    def play1(a1: Int, a2: Int): String = {
      String.valueOf(a1 + a2)
    }
    println("play1:" + play1(10, 20))

    /** 最常用 没有显示返回值的 */
    def play2(a1: Int, a2: Int) = { //可以省略类型，默认Any
      a1 - a2
      a2 + a1
    }
    println("play2:" + play2(10, 10))

    def play3(a1: Int, a2: Int) = a1 + a2 //只能一行表达式
    println("play3:" + play3(5, 5))

    def play4(a1: Int, a2: Int = 10 /*对a2指定默认值10*/) = {
      a1 + a2
    }
    println("play4:" + play4(1)) //1+10
    println("play4:" + play4(1, 1)) //1+1

    //可选参数的
    def playChange(args: Int* /*变长参数*/) = {
      for (i <- args) {
        print(i + " ")
      }
    }
    playChange(1, 2, 3, 4, 5)
    println()

    //递归实现一个数字的阶乘
    def factorial(n: Int): Int /*要指定返回值类型*/ = {
      if (n == 1)
        n
      else
        factorial(n - 1) * n
    }
    println("factorial(4):" + factorial(4)) //4*3*2*1

    /** ()Unit返回值的函数称之为方法(过程)，方法强调过程，函数强调结果 */

    //没有等号的 只能是过程
    def play6(msg: String) {
      println(msg)
    }
    println(play6("blank"))


    //匿名函数
    /**
      * 1、匿名函数右边的函数体应该使用 => 符号来指定
      * 2、匿名函数的函数体，可以是一个包含多行代码的代码块
      * 3、函数的类型，为参数个数的类型
      */
    val play7 = () => 0 //    ()=>0最省的匿名函数
    println(play7)  //<function0>
    println(play7())  //  0

    //记住这个匿名函数吧好吗 1)没有def play 没有返回类型
    val play8 = (a1: Int, a2: Int) => {
      a1 + a2
      a2 - 10 + a1
    }
    println(play8)  //<function2>
    println(play8(10, 20))  //  20

    //元组
    //Tunple
    println(Tuple2(1, 2))
  }
}
