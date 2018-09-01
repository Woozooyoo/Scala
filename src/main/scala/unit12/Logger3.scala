package unit12

/**
  * 叠加特质的执行顺序
  * 1、动态混入：右向左执行
  * 2、情景：混入的多个特质，是继承了同一个特质
  * 3、super关键字为向左调用
  * 4、如果左边没有特质了，则调用父特质中的方法
  */
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
    super.log(new java.util.Date() + " " + msg)
  }
}

trait ShortLogger3 extends ConsoleLogger3 {
  override def log(msg: String) {
    super.log(if (msg.length <= 15) msg else s"${msg.substring(0, 12)}...")
  }
}

class Account3 {
  protected var balance = 0.0
}

abstract class SavingsAccount3 extends Account3 with Logger3 {
  def withdraw(amount: Double) {
    if (amount > balance) log("余额不足")
    else balance -= amount
  }
}

object Main3 extends App {
  val acct1 = new SavingsAccount3 with TimestampLogger3 with ShortLogger3
  acct1.withdraw(100)

//  val acct2 = new SavingsAccount3 with ShortLogger3 with TimestampLogger3
//  acct2.withdraw(100)
}

