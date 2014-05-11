import java.io.InputStream

trait DataAccessHandler[ConnectionHandle,InputData,OutputData] {
  def connect: ConnectionHandle
  def retrieve(handle: ConnectionHandle): InputData
  def process(input: InputData): OutputData
  def close(handle: ConnectionHandle)

  def execute: OutputData = {
    val handle = connect
    val input = retrieve(handle)
    val output = process(input)
    close(handle)
    output
  }
}

object XmlDataAccessHandler extends DataAccessHandler[InputStream,String,String] {
  def connect = getClass.getResource("zad2.xml").openStream()
  def retrieve(handle: InputStream): String =
    scala.io.Source.fromInputStream(handle).mkString
  def process(input: String): String = {
    val regx = "<(.*) (.*)>".r
    regx.findAllMatchIn(input).map(_.toString()).maxBy(_.size)
  }
  def close(handle: InputStream) {
    handle.close()
  }
}

val xmlResult = XmlDataAccessHandler.execute

object DbDataAccessHandler extends DataAccessHandler[Unit,List[(String, Int)],Int] {
  def connect = ()
  def retrieve(handle: Unit): List[(String, Int)] =
    List(("item1", 10), ("item2", 70), ("item3", 22), ("item4", 3))
  def process(input: List[(String, Int)]): Int =
    input.map(_._2).sum
  def close(handle: Unit) {}
}

val dbResult = DbDataAccessHandler.execute


