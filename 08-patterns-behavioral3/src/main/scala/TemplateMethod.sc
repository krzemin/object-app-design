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




