package unit05_collection

object CollectionTest {

  def main(args: Array[String]): Unit = {
    // 编写方法： 从0到n的随机数中，挑选可以被10整除的m个数
    def filter(item: List[Int]): Int = {
      val i = for (e <- item if e % 10 == 0) yield e
      i.size
    }

    println(filter(List(10, 20, 22, 33)))

    val node = Node("A", "B", "C", "D", "E")
    //    node.showLink()

    val revNode = node.getReverseNode() //实现该方法，获得反向的链表
    revNode.showLink() //打印结果应该是  E->D->C->B->A
  }

}

object Node {
  //初始化链表结构
  def apply(values: String*): Node = {
    var preNode: Node = null
    var headNode: Node = null
    for (value <- values) {
      val curNode: Node = new Node(value)
      if (headNode == null) headNode = curNode
      else preNode.nextNode = curNode
      preNode = curNode
    }
    headNode;
  }
}


class Node(val value: String) {
  var nextNode: Node = null

  //打印链表结构
  def showLink(): Unit = {
    var tmpNode = this
    while (tmpNode != null) {
      print(tmpNode.value)
      if (tmpNode.nextNode != null) print("->")
      tmpNode = tmpNode.nextNode
    }
    println()
  }

  // 获得反向的链表
  def getReverseNode(): Node = {
    var oriNode = this
    var headNode: Node = null
    var bottomNode: Node = null
    while (oriNode != null) {
      val value = oriNode.value
      val curNode = new Node(value)
      headNode = curNode
      headNode.nextNode = bottomNode
      bottomNode = curNode

      oriNode = oriNode.nextNode
    }
    headNode
  }

}
