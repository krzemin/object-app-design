import org.joda.time._
import java.io._

object LoggingProxy extends App {

  class LoggingOutputStream(os: OutputStream) extends OutputStream {
    override def write(c: Int): Unit = {
      println(s"write($c) called at ${DateTime.now()}")
      os.write(c)
    }
  }

  class LoggingInputStream(is: InputStream) extends InputStream {
    override def read(): Int = {
      println(s"read() called at ${DateTime.now()}")
      val c = is.read()
      println(s"read() returned $c at ${DateTime.now()}")
      c
    }
  }

  val origText = "abc"
  println(s"original text is $origText")

  val baos = new ByteArrayOutputStream(10)
  val cos = new Decorator.CaesarOutputStream(baos, 5)
  val los = new LoggingOutputStream(cos)
  los.write(origText.getBytes)

  println(baos.toString)

  val bais = new ByteArrayInputStream(baos.toByteArray)
  val cis = new Decorator.CaesarInputStream(bais, -5)
  val lis = new LoggingInputStream(cis)
  val isr = new InputStreamReader(lis)
  val br = new BufferedReader(isr)

  println(br.readLine())

}
