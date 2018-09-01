/*
 *
 * 类的成员之 ： 内部类
 *
 * 内部类 ： 在一个A类的内部定义一个类B，类A叫做外部类，类B叫做内部类
 *
 * 内部类的分类 ： 成员内部类  （静态内部类，非静态内部类）vs 局部内部类
 *
 * 内部类
 * 		作为一个外部类的成员来说：
 * 				①可以使用四种权限修饰符
 * 				②可以使用static修饰
 * 				③内部类可以访问外部类的属性和方法
 *
 * 		作为一个类来说:
 * 				①可以有类的成员，属性，构造器，方法，代码块等
 * 				②内部类也可以被继承
 *
 * 问题？
 * 		1.如何创建静态内部类和非静态内部类的对象？
 * 		2.内部类如何调用外部的属性和方法？
 * 		3.如何使用局部内部类？
 */
//import com.atguigu.java3.Person.A;
public class InnerClassTest {

	public static void main(String[] args) {

		//1.如何创建静态内部类和非静态内部类的对象？
		//创建非静态内部类
		Person.A a = new Person().new A();
		a.say("ccc");
		System.out.println(a.address);
		//创建静态内部类的对象
		Person.B b = new Person.B();
		b.info();

		System.out.println("---------------------------");
		//2.内部类如何调用外部类的成员

		a.say("ccc");

		b.getSex("人妖");

		System.out.println("-----------------------------");
		//3.如何使用局部内部类（了解）

		InnerClassTest innerClassTest = new InnerClassTest();
		//获取局部内部类的对象
		Creature ct = innerClassTest.getClassInstance();
		ct.show();
	}

	/*
	 * 局部内部类
	 */
	public Creature getClassInstance(){
		class Student extends Creature{
			public void show(){
				System.out.println("show student");
			}
		}

		return new Student();
	}
}

class Creature{
	public void show(){
		System.out.println("cccc");
	}
}


class Person{
	String name = "person";
	int age;
	static String sex = "女";

	public void show(){
		System.out.println("person show");
	}

	public static void show2(){
		System.out.println("show2");
	}
	//非静态内部类
	class A{
		String address = "aaa";
		String name = "A";
		public void say(String name){
//			System.out.println(" say goodbye");
			System.out.println(name + this.name + " Person-name=" + Person.this.name);
			System.out.println(age);
			this.show(); //调用本类中的方法
			Person.this.show(); //调用外部类中的方法
		}

		public void show(){
			System.out.println("A show");
		}
	}

	//静态内部类
	/*
	 * 静态内部类，不能调用外部类中非静态的属性，不能调用外部类中非静态的方法
	 *
	 */
	static class B{
		String sex = "男";
		public void info(){
//			System.out.println("info");

		}

		public void getSex(String sex){
			System.out.println(sex);
			System.out.println(this.sex);
			System.out.println(Person.sex); //调用外部类的类变量
			show2(); //调用本类中的方法
			Person.show2();//调用外部类中的方法
		}

		public void show2(){
			System.out.println("b show2");
		}
	}

	class D extends A{

	}

	public void say(){
		//局部内部类
		class C{

		}
	}
}


