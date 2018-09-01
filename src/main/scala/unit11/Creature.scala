package unit11

class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature {
  override val range = 20
}

class Ant2 extends {
  override val range = 20
} with Creature

