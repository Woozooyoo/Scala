package unit12_traits

object Trait {
  def main(args: Array[String]): Unit = {
    /**特质 构造器中  不能有参数*/

    trait Logger {
      def log(msg: String): Unit
    }

    //    val logger = new ConsoleLogger
    //    logger.log("hello")
    println("=============3 Traits with Concrete Implementations带有具体实现的特质===============")
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

    // 继承一个类的同时并混入特质
    val drawAccount = new DrawAccount1
    drawAccount.withdraw(10)

    println("=============4 Objects with Traits带有特质的对象，动态混入===============")
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

    val account = new DrawsAccount2 with ConsoleLogger //动态混入实现了log方法的ConsoleLogger
    account.withdraw(100)

    /**
      * 叠加特质的执行顺序
      * 1、动态混入：右向左执行
      * 2、情景：混入的多个特质，是继承了同一个特质
      * 3、super关键字为向左调用
      * 4、如果左边没有特质了，则调用父特质中的方法
      */

    println("=============5 Layered Traits叠加在一起的特质 的动态混入===============")
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


    println("=============6 Overriding Abstract Methods in Traits在特质中重写抽象方法===============")
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

    println("=============7  Traits for Rich Interfaces当做富接口使用的特质===============")
    //富特质 即该特质中既有抽象方法，又有非抽象方法
    trait Logger7 {
      def log(msg: String) //抽象

      def info(msg: String) {
        log("INFO: " + msg)
      }

      def warn(msg: String) {
        log("WARN: " + msg)
      }

      def severe(msg: String) {
        log("SEVERE: " + msg) //非抽象方法调用了抽象方法
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

    //动态混入 DrawAccount7调用的severe 调用了Logger7的severe，调用了抽象log
    //with混入了ConsoleLogger7使log调用了ConsoleLogger7的非抽象log
    val acct7 = new DrawAccount7 with ConsoleLogger7
    acct7.withdraw(100)

    println("=============8 Concrete Fields in Traits特质中的具体字段===============")
    //特质中可以定义具体字段，如果初始化了就是具体字段，如果不初始化就是抽象字段。
    //混入该特质的类就具有了该字段，字段不是继承，而是简单的加入类。是自己的字段。
    trait Logger8 {
      def log(msg: String)
    }

    trait ConsoleLogger8 extends Logger8 {
      def log(msg: String) {
        println(msg)
      }
    }

    trait ShortLogger8 extends Logger8 {
      val maxLength = 8 //混入会把此字段带入类中
      abstract override def log(msg: String) {
        super.log(if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...")
      }
    }

    class Account8 {
      protected var balance = 0.0
    }

    class SavingsAccount8 extends Account8 with ConsoleLogger8 with ShortLogger8 {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    val acct8 = new SavingsAccount8
    acct8.withdraw(100)
    println(acct8.maxLength) //混入会把此字段带入类中


    println("=============9  Abstract Fields in Traits特质中的抽象字段===============")
    //特质中未被初始化的字段在具体的子类中必须被重写。
    //特质中的具体字段
    trait Logger9 {
      def log(msg: String)
    }

    trait ConsoleLogger9 extends Logger9 {
      def log(msg: String) {
        println(msg)
      }
    }

    trait ShortLogger9 extends Logger9 {
      val maxLength: Int

      abstract override def log(msg: String) {
        super.log(if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...")
      }
    }

    class Account9 {
      protected var balance = 0.0
    }

    abstract class SavingsAccount9 extends Account9 with Logger9 {
      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    val acct9 = new SavingsAccount9 with ConsoleLogger9 with ShortLogger9 {
      val maxLength = 20
    }
    acct9.withdraw(100)
    println(acct9.maxLength)

    println("=============10  Trait Construction Order特质构造顺序===============")
    //特质也是有构造器的，构造器中的内容由“字段的初始化”和一些其他语句构成
    trait Logger10 {
      println("我在Logger10特质构造器中，嘿嘿嘿。。。")

      def log(msg: String)
    }

    trait ConsoleLogger10 extends Logger10 {
      println("我在ConsoleLogger10特质构造器中，嘿嘿嘿。。。")

      def log(msg: String) {
        println(msg)
      }
    }

    trait ShortLogger10 extends Logger10 {
      val maxLength: Int
      println("我在ShortLogger10特质构造器中，嘿嘿嘿。。。")

      abstract override def log(msg: String) {
        super.log(if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...")
      }
    }

    class Account10 {
      println("我在Account10构造器中，嘿嘿嘿。。。")
      protected var balance = 0.0
    }

    class SavingsAccount10 extends Account10 with ConsoleLogger10 with ShortLogger10 {
      println("我再SavingsAccount10构造器中")
      override val maxLength: Int = 20

      def withdraw(amount: Double) {
        if (amount > balance) log("余额不足")
        else balance -= amount
      }
    }

    val acct10 = new SavingsAccount10
    acct10.withdraw(100)
    println(acct10.maxLength)

    /** 步骤总结：先extends,再从左到右的第一个父特质,从左到右的第一个特质,第二个父,当前
      * 1、调用当前类的超类构造器 Account10
      * 2、第一个特质的父特质构造器  Logger10
      * 3、第一个特质构造器  ConsoleLogger10
      * 4、第二个特质构造器的父特质构造器由于已经执行完成，所以不再执行
      * 5、第二个特质构造器  ShortLogger10
      * 6、当前类构造器  SavingsAccount10 */


    println("=============11  Initializing Trait Fields初始化特质中的字段===============")
    trait Logger11 {
      def log(msg: String)
    }

    trait FileLogger11 extends Logger11 {
      val fileName: String
      val str = new StringBuilder(fileName)

      override def log(msg: String): Unit = {
        println(str + "\t" + msg)
      }
    }

    class SavingsAccount11 {}

    //报错
    /*val acct11 = new SavingsAccount11 with FileLogger11 {
      override val fileName = "2017-11-24.log" //空指针异常 父类中str调用了子类的字段
    } //因为这是子类, 最后才定义   这个不是提前定义*/

    println("=============//提前定义===============")
    val acct11_1 = new {
      override val fileName = "acct11_1.log"
    } with SavingsAccount11 with FileLogger11
    acct11_1.log("heiheihei")


    println("=============//或提前定义在这里 new的类 extends{提前定义} with===============")
    class SavingsAccount11_2 extends {
      override val fileName = "acct11_2.log"
    } with FileLogger11

    val acct11_2 = new SavingsAccount11_2 with FileLogger11
    acct11_2.log("嘿嘿嘿")


    println("=============//或使用lazy===============")
    trait Logger11_3 {
      def log(msg: String)
    }

    trait FileLogger11_3 extends Logger11_3 {
      val fileName: String
      lazy val str = new StringBuilder(fileName) //使用lazy

      override def log(msg: String): Unit = {
        println(str + "\t" + msg)
      }
    }

    class SavingsAccount11_3 {}

    val acct11_3 = new SavingsAccount11_3 with FileLogger11_3 {
      override val fileName = "acct11_3.log"
    }
    acct11_3.log("哈哈哈")

    println("=============12 Traits Extending Classes 类继承时 不能同时继承两个父类不同的类 扩展类的特质===============")
    /*总结：
    1、特质可以继承自类，以用来拓展该类的一些功能
    2、所有混入该特质的类，会自动成为那个特质所继承的超类的子类
    3、如果混入该特质的类，已经继承了另一个类，不就矛盾了？注意，只要继承的那个类是特质超类的子类即可。
    例如：
    1) 特质可以继承自类，以用来拓展该类的一些功能*/
    trait LoggedException extends Exception {
      def log(): Unit = {
        println(getMessage)
      }
    }
    //2) 所有混入该特质的类，会自动成为那个特质所继承的超类的子类
    class UnhappyException extends LoggedException {
      override def getMessage = "UnhappyException！"
    }
    //3)  如果混入该特质的类，已经继承了另一个类，不就矛盾了？注意，只要继承的那个类是特质超类的子类即可。
    //正确：
    //类继承时 extends 一个父类的子类 同时 with一个有相同父类的特质Ok
    class UnhappyException2 extends IndexOutOfBoundsException with LoggedException {
      override def getMessage = "UnhappyException2！"
    }
    //错误：
    //类继承时 extends otherClass 同时 with一个有父类的特质会出错
//    import javax.swing.JFrame
//    class UnhappyException3 extends JFrame with LoggedException {
//      override def getMessage = "UnhappyException3！"
//    }

    //OK：
    trait TraitClass{}/*此特质没有父类 Ok*/
    class Account12{}
    class UnhappyException4 extends Account12 with TraitClass/*此特质没有父类 Ok*/ {
    }

  }
}
