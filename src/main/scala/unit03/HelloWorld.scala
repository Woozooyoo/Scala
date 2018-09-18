package unit03

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("He11o sca1a")
    val a = 10
    var b = 20
    b = 30
    println(a + "," + b)
    println(a + b)
    println(a.*(b))

    val res0 = 8 * 5 + 2

    val res1: String = ("Hello, " + res0).toUpperCase

    println(s"res1 = $res1")


    val answer = 8 * 5 + 2
    //  answer = 0 // This will give an error

    val d: Double = 0.5 * answer

    println(s"d = ${d}")

    var counter = 0
    counter = 1 // Ok, can change a var
    counter += 1 // Increments counter—Scala has no ++
    println(counter)

    println(1.toString)

    println(1.to(10))

    println("HelloW".intersect("World"))


    val x: BigInt = 1234567890
    println(x * x * x)

    import scala.math._

    println(sqrt(2))
    println(pow(2, 3))
    println(min(3, Pi))

    println("Hello".distinct)

    println("Hello" (4))

    println("Hello".apply(4))

    println(BigInt("1234567890"))

    println(BigInt.apply("1234567890"))

    println(BigInt("1234567890") * BigInt("112358111321"))

    println("Hello, World!".count(_.isUpper))
    println("Bierstube".containsSlice('r'.to('u')))
    println("Scala".sorted)
  }
}
