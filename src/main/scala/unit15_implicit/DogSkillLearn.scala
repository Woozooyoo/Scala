package unit15_implicit

class Dog {
  val name = "金毛"
}

class Skill {
  def fly(animal: Dog, skill: String) = println(animal.name + "已领悟" + skill)
}

object Learn {
  implicit def learningType(s: Dog) = new Skill
}


