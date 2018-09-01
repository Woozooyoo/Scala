package unit04_function

object Iterable {
  def main(args: Array[String]): Unit = {
    val arr1 = new Array[Int](10)
    println(arr1 mkString (" "))

    val arr2 = Array(1, 2, 3)
    println(arr2 mkString (" "))

    arr2(2) = 10
    println(arr2 mkString (" "))

    val arr3 = arr2 :+ 99
    println(arr3 mkString (" "))
  }
}
