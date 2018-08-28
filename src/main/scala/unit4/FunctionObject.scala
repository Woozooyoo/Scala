package unit4

object FunctionObject {
  def main(args: Array[String]): Unit = {
    def play1(a1: Int, a2: Int): String = {
      String.valueOf(a1 + a2)
    }
    println(play1(10, 20))

    def play2(a1: Int, a2: Int) = {
      a1 + a2
    }
    println(play2(10, 10))

    val play7 = () => 0
    println(play7)
    println(play7())

    val play8 = (a1: Int, a2: Int) => {
      a1 + a2
      a2 - 10 + a1
    }
    println(play8)
    println(play8(20, 10))

    println(Tuple2(1, 2))
  }
}
