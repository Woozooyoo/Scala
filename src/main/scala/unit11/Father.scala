package unit11

abstract class Father(age: Int) {
  val id:Int
  val name: String
  def play:Unit
}

class Child(age: Int) extends Father(age){
  override val id: Int = 1
  override val name: String = "Nick"

  override def play: Unit = {
    println(name)
  }
}
