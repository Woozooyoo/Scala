package unit12

object TraitSyllabus {
  def main(args: Array[String]): Unit = {
    //当做接口来实现
//    val logger = new ConsoleLogger
//    logger.log("hello")
    // 继承一个类的同时并混入特质
    val drawAccount = new DrawAccount2
    drawAccount.withdraw(10)
  }
}
