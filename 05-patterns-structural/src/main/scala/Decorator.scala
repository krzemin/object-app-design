
import java.io._

object Decorator extends App {

  class CaesarInputStream(is: InputStream, shift: Int) extends InputStream {
    override def read(): Int = shift + is.read()
  }

  class CaesarOutputStream(os: OutputStream, shift: Int) extends OutputStream {
    override def write(c: Int): Unit = os.write(shift + c)
  }

  val bais = new ByteArrayInputStream("abc".getBytes)
  val cis = new CaesarInputStream(bais, 5)

  //val bis = new BufferedInputStream(cis)
  //val isr = new InputStreamReader(bis)
  //val br = new BufferedReader(isr)
  //println(br.readLine())




  val sw = new StringWriter
  sw.write("abc")
  //sw

  println(sw.getBuffer.toString)



}