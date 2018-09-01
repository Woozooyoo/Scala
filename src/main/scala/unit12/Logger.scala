package unit12

trait Logger {
  def log(msg: String): Unit
}

trait ConsoleLogger {
  def log(msg: String) {
    println(msg)
  }
}

class Account {
  protected var balance = 0.0
}

class DrawAccount2 extends Account2 with ConsoleLogger2 {
  def withdraw(amount: Double) {
    if (amount > balance) log("余额不足")
    else balance -= amount
  }
}
