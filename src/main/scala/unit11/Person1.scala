package unit11

class Person1(val name: String, var age: Int) {
  println("主构造器已经被调用")
  val school = "五道口职业技术学院"

  def sleep = "8 hours"

  override def toString = "我的学校是：" + school + ",我的名字和年龄是：" + name + "," + age
}


class Person2(name:String, age:Int) extends Person1(name, age){
  override val school: String = "清华大学"
}

