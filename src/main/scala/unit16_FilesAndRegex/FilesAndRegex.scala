package unit16_FilesAndRegex

import scala.io.{Source, StdIn}

object FilesAndRegex {
  def main(args: Array[String]): Unit = {
    println("=============1  文件读取 Source.fromFile()===============")
    //文件读取
    val file1 = Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt")
    val lines = file1.getLines
    for (line <- lines) {
      println(line)
    }

    val file1_2 = Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt")
    println("=============   文件内容转数组===============")
    val lines1_2 = file1_2.getLines
    println(lines1_2.toArray mkString " ")

    val file1_3 = Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt")
    println("=============   文件内容转字符串===============")
    println(file1_3.mkString)

    val file1_4 = Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt")
    println("=============   getLines.filter.mkstring===============")
    val lines1_4 = file1_4.getLines.filter(l=>l.contains("二")).mkString(" ")
    println(lines1_4)

    file1.close
    file1_2.close
    file1_3.close
    file1_4.close

    println("=============2  读取字符===============")
    //由于Source.fromFile直接返回的就是Iterator[Char]，所以可以直接对其进行迭代，按照字符访问里边每一个元素。
    val file2 =Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt", "UTF-8")
    for(ch <- file2){
      println(ch)
    }
    file2.close

    println("=============3  按正则表达式分割 读取===============")
    //如果想将以某个字符或某个正则表达式分开的字符成组读取，可以这么做：
    val file3 = Source.fromFile("E:\\Study\\BDcourse\\15_大数据技术之scala\\test.txt")
    val tokens = file3.mkString.split(",")
    println(tokens.mkString(" "))
    file3.close

    println("=============4  读取网络资源===============")
//    val webFile = Source.fromURL("http://www.baidu.com")
//    webFile.foreach(print)
//    webFile.close()

    println("=============   写入数据到文件===============")
    import java.io.{File, PrintWriter}
    val writer = new PrintWriter(new File("E:\\Study\\BDcourse\\15_大数据技术之scala\\写入数据到文件.txt"))
    for (i <- 1 to 100)
      writer.println(i)
    writer.close()

    println("=============   控制台操作===============")
    //控制台交互--新API
    print("请输入内容(新API):")
    val consoleLine2 = StdIn.readLine()
    println("刚才输入的内容是:" + consoleLine2)

    println("=============5  序列化===============")
    @SerialVersionUID(1L) class Person extends Serializable{
      override def toString = name + "," + age

      val name = "Nick"
      val age = 20

    }
    import java.io.{FileOutputStream, FileInputStream, ObjectOutputStream, ObjectInputStream}
    //序列化
    val nick = new Person
    val out = new ObjectOutputStream(new FileOutputStream("Nick.txt"))
    out.writeObject(nick)
    out.close()

    //反序列化
    val in = new ObjectInputStream(new FileInputStream("Nick.txt"))
    val saveNick = in.readObject()
    in.close()
    println(saveNick)

    println("=============6  进程控制===============")
    //我们可以使用scala来操作shell，scala提供了scala.sys.process包提供了用于shell程序交互的工具。
    //1)  执行shell

    /**import sys.process._
    "ls -al /"!;
    "ls -al /"!!;*/
    //尖叫提示：!和!!的区别在于：process包中有一个将字符串隐式转换成ProcessBuild对象的功能，
    // 感叹号就是执行这个对象，单感叹号的意思就是程序执行成功返回0，执行失败返回非0，
    // 如果双感叹号，则结果以字符串的形式返回。
    //2)  管道符
    /**import sys.process._
    "ls -al /" #| "grep etc" !*/
    //3)  将shell的执行结果重定向到文件
    /**import sys.process._
    "ls -al /" #| "grep etc" !;
    "ls -al /" #>> new File("output.txt") !;*/

    //尖叫提示：注意，每一个感叹号后边，有分号结束
    /*scala进程还可以提供：
    p #&& q操作，即p任务执行成功后，则执行q任务。
    p #|| q操作，即p任务执行不成功，则执行q任务。
    既然这么强大，那么crontab + scala + shell，就完全不需要使用oozie了。*/

    println("=============7  正则表达式===============")
    //我们可以通过正则表达式匹配一个句子中所有符合匹配的内容，并输出：
    import scala.util.matching.Regex
    val pattern1 = new Regex("(S|s)cala")
    val pattern2 = "(S|s)cala".r
    val str = "Scala is scalable and cool"
    println((pattern1 findAllIn str).mkString(","))
    println((pattern2 findAllIn str).mkString(","))

  }
}
