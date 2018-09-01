package unit12_traits

object Trait {
  def main(args: Array[String]): Unit = {

    trait Logger {
      def log(msg: String): Unit
    }

    println("=============3 带有具体实现的特质===============")
    trait ConsoleLogger {
      def log(msg: String) {
        println(msg)
      }
    }

    class Account {
      protected var balance = 0.0
    }

    class DrawAccount1 extends Account with ConsoleLogger {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    //    val logger = new ConsoleLogger
    //    logger.log("hello")
    // 继承一个类的同时并混入特质
    val drawAccount = new DrawAccount1
    drawAccount.withdraw(10)

    println("=============4 带有特质的对象，动态混入===============")
    trait Logger2 {
      def log(msg: String)
    }

    trait ConsoleLogger2 extends Logger2 {
      def log(msg: String) {
        println(msg)
      }
    }

    class Account2 {
      protected var balance = 0.0
    }

    abstract class DrawsAccount2 extends Account2 with Logger2 {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    val account = new DrawsAccount2 with ConsoleLogger
    account.withdraw(100) //动态混入实现了log方法的ConsoleLogger

    /**
      * 叠加特质的执行顺序
      * 1、动态混入：右向左执行
      * 2、情景：混入的多个特质，是继承了同一个特质
      * 3、super关键字为向左调用
      * 4、如果左边没有特质了，则调用父特质中的方法
      */

    println("=============5叠加在一起的特质 的动态混入===============")
    trait Logger3 {
      def log(msg: String);
    }

    trait ConsoleLogger3 extends Logger3 {
      def log(msg: String) {
        println(msg)
      }
    }

    trait TimestampLogger3 extends ConsoleLogger3 {
      override def log(msg: String) {
        super.log(new java.util.Date() + " " + msg) //super.log不是调用ConsoleLogger3.log方法
      }
    }

    trait ShortLogger3 extends ConsoleLogger3 {
      override def log(msg: String) {
        super.log(if (msg.length <= 15) msg else s"${msg.substring(0, 15)}...") //super.log不是调用ConsoleLogger3.log方法
      }
    }

    class Account3 {
      protected var balance = 0.0
    }

    abstract class DrawsAccount3 extends Account3 with Logger3 {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    //继承多个相同父特质的类， 从右到左执行特质方法
    //super关键字为调用左边特质的方法
    //如果左边没有特质了，则调用父特质中的方法
    //如果想要调用具体特质的方法，可以指定：super[ConsoleLogger].log(…).
    //其中的泛型必须是该特质的直接超类类型
    println("with TimestampLogger3 with ShortLogger3") //先"余额不足"<=15------再"余额不足"
    val acct1 = new DrawsAccount3 with TimestampLogger3 with ShortLogger3
    acct1.withdraw(100)

    println("with ShortLogger3 with TimestampLogger3") //先时间戳 -------再传到 subString
    val acct2 = new DrawsAccount3 with ShortLogger3 with TimestampLogger3
    acct2.withdraw(100)


    println("=============6 在特质中重写抽象方法===============")
    trait Logger6 {
      def log(msg: String)
    }

    //因为有super，Scala认为log还是一个抽象方法
    trait TimestampLogger6 extends Logger6 {
      abstract override def log(msg: String) { //用抽象的方式覆写抽象方法
        super.log(new java.util.Date() + " " + msg)
      }
    }

    trait ShortLogger6 extends Logger6 {
      abstract override def log(msg: String) {
        super.log(if (msg.length <= 15) msg else s"${msg.substring(0, 12)}...")
      }
    }

    trait ConsoleLogger6 extends Logger6 {
      override def log(msg: String) {
        println(msg)
      }
    }

    class Account6 {
      protected var balance = 0.0
    }

    abstract class SavingsAccount6 extends Account6 with Logger6 {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    println("with ConsoleLogger6 with TimestampLogger6 with ShortLogger6")
    //这里可以根据12.5的知识点理解此处 ConsoleLogger6真正输出 后两个都是抽象的方法，只是加工log
    val acct6 = new SavingsAccount6 with ConsoleLogger6 with TimestampLogger6 with ShortLogger6
    acct6.withdraw(100)

    println("=============7  当做富接口使用的特质===============")
    //即该特质中既有抽象方法，又有非抽象方法
    //富特质
    trait Logger7 {
      def log(msg: String) //抽象

      def info(msg: String) {
        log("INFO: " + msg)
      }

      def warn(msg: String) {
        log("WARN: " + msg)
      }

      def severe(msg: String) {
        log("SEVERE: " + msg)
      }
    }

    trait ConsoleLogger7 extends Logger7 {
      def log(msg: String) {
        println(msg)
      }
    }

    class Account7 {
      protected var balance = 0.0
    }

    abstract class DrawAccount7 extends Account7 with Logger7 {
      def withdraw(amount: Double) {
        if (amount > balance) severe("余额不足")
        else balance -= amount
      }
    }

    val acct7 = new DrawAccount7 with ConsoleLogger7
    acct7.withdraw(100)


  }
}
