public class Other {
	public static void main(String[] args) {

		//成员内部类 通过 外部类的实例化对象outter new 内部类构造器
		Outter.Inner inner = new Outter ().new Inner ();
		//静态内部类 通过 new 外部类类名.内部类构造器
		Outter.Inner2 inner2 = new Outter.Inner2 ();

	}
}

class Outter {
	class Inner {

	}

	static class Inner2 {

	}
}
