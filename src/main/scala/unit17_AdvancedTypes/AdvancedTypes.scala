package unit17_AdvancedTypes

import scala.collection.mutable


object AdvancedTypes {

  class A {}

  def main(args: Array[String]): Unit = {
    println("=============1  getClass、类型(typeOf)、类(classOf)的区别===============")
    val a = new A
    import scala.reflect.runtime.universe._
    println(a.getClass) //res2: Class[_ <: A] = class A
    //class unit17_AdvancedTypes.AdvancedTypes$A
    println(typeOf[A]) //res1: reflect.runtime.universe.Type = A
    //unit17_AdvancedTypes.AdvancedTypes.A
    println(classOf[A]) //res3: Class[A] = class A

    println("=============5  Type Aliases类型别名===============")
    class Document {
      def play(x: Index): Unit = println(x)
    }

    type Index = mutable.HashMap[String, (Int, Int)]

    val d = new Document
    val hm = new mutable.HashMap[String, (Int, Int)]()
    hm("Joseph") = (24, 24)
    hm("Autumn") = (22, 22)
    d.play(hm)

    val ind = new Index
    ind += ("Sumer" -> (21, 21))
    ind += ("As time went by" -> (903, 903))
    d.play(ind)

    println("=============6  Structural Types结构类型===============")
    // 结构类型是指一组关于抽象方法、字段和类型的规格说明，
    // 你可以对任何具备play方法的类的实例调用play方法，
    // 这种方式比定义特质更加灵活，是通过反射进行调用的：
    type X = {def play()} //type关键字是把 = 后面的内容命名为别名。 一个结构类型

    def init(res: X) = res.play //本地方法  参数是X结构类型,里面有play方法,所以可以play

    init(new { //这是一个new 出来的 没名的 X结构类型的对象
      def play() = println("new type X结构类型的Played")
    })

    init(new { //这是一个new 出来的 X结构类型的对象
      def play() = println("再一次new type X结构类型的Play")
    })

    object A {
      def play() {
        println("object A play")
      }
    }
    init(A) //A有play方法

    class Structure {
      def play() = println("class Structure play")
    }
    init(new Structure) //Structure有play 方法
    //总结：结构类型，简单来说，就是只要是传入的类型，符合之前定义的结构的，都可以调用。

    println("=============8  Infix Types中置类型===============")
    //中置类型是一个带有两个类型参数的类型，以中置语法表示，
    //比如可以将Map[String, Int]表示为：
    //String Map Int
    val scores: String Map Int = Map("Fred" -> 42)
    println(scores)

    println("=============9    Self Types自身类型===============")
    //self => 这句相当于给this起了一个别名为self：
    class B {
      self => //this别名
      val x = 2
      def foo = self.x + this.x
    }
    println(new B foo)

    //self不是关键字，可以用除了this外的任何名字命名(除关键字)。
    // 就上面的代码，在A内部，可以用this指代当前对象，也可以用self指代，两者是等价的。
    //它的一个场景是用在有内部类的情况下：
    class Outer {
      outer =>
      val v1 = "here"

      class Inner {
        val v1 = "there"
        println(outer.v1) // 用outer表示外部类，相当于Outer.this
      }

    }
    //对于this别名 self =>这种写法形式，是自身类型(self type)的一种特殊方式。
    // self在不声明类型的情况下，只是this的别名，所以不允许用this做this的别名。

    println("=============10  运行时反射===============")
    //scala编译器会将scala代码编译成JVM字节码，编译过程中会擦除scala特有的一些类型信息，
    //从scala-2.10起，scala实现了自己的反射机制，我们可以通过scala的反射机制得到scala的类型信息。
    println("=============10.1  获取运行时类型信息===============")
    //scala运行时类型信息是保存在TypeTag对象中，编译器在编译过程中将类型信息保存到TypeTag中，并将其携带到运行期。我们可以通过typeTag方法获取TypeTag类型信息。
    import scala.reflect.runtime.universe._
    val typeTagList = typeTag[List[Int]] //得到了包装Type对象的TypeTag对象
    println(typeTagList)
    //或者使用：
    typeOf[List[Int]] //直接得到了Type对象
    //尖叫提示：Type对象是没有被类型擦除的
    //我们可以通过typeTag得到里面的type，再通过type得到里面封装的各种内容：
    import scala.reflect.runtime.universe._
    val typeTagList2 = typeTag[List[Int]]
    println(typeTagList2)
    println(typeTagList2.tpe)
    println(typeTagList2.tpe.decls.take(10))

    println("=============10.2  运行时类型实例化===============")
    //我们已经知道通过Type对象可以获取未擦除的详尽的类型信息，下面我们通过Type对象中的信息找到构造方法并实例化类型的一个对象：

    //得到JavaUniverse用于反射
    val ru = scala.reflect.runtime.universe
    //得到一个JavaMirror，一会用于反射Person.class
    val mirror = ru.runtimeMirror(this.getClass.getClassLoader)
    //得到Person类的Type对象后，得到type的特征值并转为ClassSymbol对象
    val classPerson = ru.typeOf[Person].typeSymbol.asClass
    //得到classMirror对象
    val classMirror = mirror.reflectClass(classPerson)
    //得到构造器Method
    val constructor = ru.typeOf[Person].decl(ru.termNames.CONSTRUCTOR).asMethod
    //得到MethodMirror
    val constructorMethodMirror = classMirror.reflectConstructor(constructor)
    //实例化该对象
    val p = constructorMethodMirror("Mike", 1)
    println(p)

    println("=============10.3  运行时类成员的访问===============")

    //获取Environment和universe
    val ru3 = scala.reflect.runtime.universe
    //获取对应的Mirrors,这里是运行时的
    val mirror3 = ru3.runtimeMirror(this.getClass.getClassLoader)
    //得到Person类的Type对象后，得到type的特征值并转为ClassSymbol对象
    val classPerson3 = ru3.typeOf[Person3].typeSymbol.asClass
    //用Mirrors去reflect对应的类,返回一个Mirrors的实例,而该Mirrors装载着对应类的信息
    val classMirror3 = mirror3.reflectClass(classPerson3)
    //得到构造器Method
    val constructor3 = ru3.typeOf[Person3].decl(ru3.termNames.CONSTRUCTOR).asMethod
    //得到MethodMirror
    val constructorMethodMirror3 = classMirror3.reflectConstructor(constructor3)
    //实例化该对象
    val p3 = constructorMethodMirror3("Mike", 1)
    println(p3)


    //反射方法并调用
    val instanceMirror3 = mirror3.reflect(p3)
    //得到Method的Mirror
    val myPrintMethod3 = ru3.typeOf[Person3].decl(ru3.TermName("myPrint")).asMethod
    //通过Method的Mirror索取方法
    val myPrint3 = instanceMirror3.reflectMethod(myPrintMethod3)
    //运行myPrint方法
    myPrint3()

    //得到属性Field的Mirror
    val nameField3 = ru3.typeOf[Person3].decl(ru3.TermName("name")).asTerm
    val name3 = instanceMirror3.reflectField(nameField3)
    println(name3.get)
    /**/

  }
  class Person(name: String, age: Int) {
    def myPrint() = {
      println(name + "," + age)
    }
  }
  class Person3(name: String, age: Int) {
    def myPrint() = {
      println(name + "," + age)
    }
  }
}
