package unit12

//动态混入
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

abstract class SavingsAccount2 extends Account2 with Logger2 {
  def withdraw(amount: Double) {
    if (amount > balance) log("余额不足")
    else balance -= amount
  }
}

object Main extends App {
  val account = new SavingsAccount2 with ConsoleLogger2
  account.withdraw(100)
}
