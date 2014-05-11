import java.io.InputStream

trait DataAccessStrategy[ConnectionHandle,InputData,OutputData] {
  def connect: ConnectionHandle
  def retrieve(handle: ConnectionHandle): InputData
  def process(input: InputData): OutputData
  def close(handle: ConnectionHandle)
}


trait Context[H,I,O] {
  self: DataAccessStrategy[H,I,O] =>

  def execute = {
    val handle = connect
    val input = retrieve(handle)
    val output = process(input)
    close(handle)
    output
  }
}

trait XmlDataAccessStrategy extends DataAccessStrategy[InputStream,String,String] {
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

trait DbDataAccessStrategy extends DataAccessStrategy[Unit,List[(String, Int)],Int] {
  def connect = ()
  def retrieve(handle: Unit): List[(String, Int)] =
    List(("item1", 10), ("item2", 70), ("item3", 22), ("item4", 3))
  def process(input: List[(String, Int)]): Int =
    input.map(_._2).sum
  def close(handle: Unit) {}
}

object XmlDataProcessor extends Context[InputStream,String,String] with XmlDataAccessStrategy

val xmlResult = XmlDataProcessor.execute

object DbDataProcessor extends Context[Unit,List[(String, Int)],Int] with DbDataAccessStrategy

val dbResult = DbDataProcessor.execute


