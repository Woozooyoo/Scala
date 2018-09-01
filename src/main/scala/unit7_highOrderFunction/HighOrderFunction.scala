package unit7_highOrderFunction

object HighOrderFunction {
  def main(args: Array[String]): Unit = {

    //作为参数的函数
    def plus(x: Int) = 3 + x
    val result1 = Array(1, 2, 3, 4).map(plus)
    println(result1.mkString(","))

    //1)  高阶函数的使用1
    def highOrderFunction1(f: Double => Double) = f(10)
    def minus7(x: Double) = x - 7
    val result2 = highOrderFunction1(minus7)
    println(result2)
    //4  参数（类型）推断
    // 传入函数表达式
    println(highOrderFunction1((x: Double) => 3 * x))
    // 参数推断省去类型信息 单个参数可以省去括号
    println(highOrderFunction1(x => 3 * x))
    // 如果变量旨在=>右边只出现一次，可以用_来代替
    println(highOrderFunction1(3 * _))

    println(List(1, 2, 3, 4).map((x: Int) => x + 1))
    println(List(1, 2, 3, 4).map(x => x + 1)) //x是匿名函数的参数

    //  高阶函数的使用2
    def play(x: String, y: Int, f: (String, Int) => String) = f(x, y)
    def f(x: String, y: Int): String = x + y
    val h1 = play("Hello", 10, f)
    println(h1)


    //2)  高阶函数同样可以返回函数类型
    println("============5  闭包")
    def minusxy(x: Int) = /*这后面是一个单独函数，为什么能访问x*/(y: Int) => x - y
    println(minusxy(10)(20))

    //闭包就是一个函数把外部的那些不属于自己的对象也包含(闭合)进来。
    //1) 匿名函数(y: Int) => x -y嵌套在minusxy函数中。
    //2) 匿名函数(y: Int) => x -y使用了该匿名函数之外的变量x
    //3) 函数minusxy返回了引用了局部变量的匿名函数
    def minusxy1(x: Int) = (y: Int) => x - y
    val f1 = minusxy1(10) /*x*/
    println(f1(20 /*y*/))
    //    此处f1函数就叫闭包。

    println("==========Curry应用")
    //函数编程中，接受多个参数的函数都可以转化为接受单个参数的函数，
    // 这个转化过程就叫柯里化，柯里化就是证明了函数只需要一个参数而已。
    // 其实我们刚才的学习过程中，已经涉及到了柯里化操作，
    // 所以这也印证了，柯里化就是以函数为主体这种思想发展的必然产生的结果。
    def mul(x: Int, y: Int) = x * y
    println(mul(10, 10))

    def mulCurry(x: Int) = (y: Int) => x * y
    println(mulCurry(10)(9))

    def mulCurry2(x: Int)(y:Int) = x * y
    println(mulCurry2(10)(8))

//    2)  柯里化的应用
//    比较两个字符串在忽略大小写的情况下是否相等，注意，这里是两个任务：
//    1、全部转大写（或小写）
//    2、比较是否相等
//    针对这两个操作，我们用一个函数去处理的思想，其实无意间也变成了两个函数处理的思想。示例如下：
    val a = Array("Hello", "World")
    val b = Array("hello", "world")
    println(a.corresponds (b)(_ equalsIgnoreCase _ ) )  //Curry
    //    其中corresponds函数的源码如下：
  /*def corresponds[B](that: GenSeq[B])(p: (A,B) => Boolean): Boolean = {
      val i = this.iterator
      val j = that.iterator
      while (i.hasNext && j.hasNext)
        if (!p(i.next(), j.next()))
          return false

      !i.hasNext && !j.hasNext
    }*/


    println("===============控制抽象")  //线程池应用
    def runOnThread(f1: () => Unit): Unit = {
      new Thread {
        override def run() = {
          f1()
        }
      }.start()
    }

    runOnThread {/*这是传入函数，省略了()*/
      () =>
        println("干活咯")
        Thread.sleep(5000)
        println("活干完了")
    }

  }
}
