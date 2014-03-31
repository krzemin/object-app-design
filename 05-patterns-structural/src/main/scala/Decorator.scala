import java.io._

object Decorator extends App {

  class CaesarInputStream(is: InputStream, shift: Int) extends InputStream {
    override def read(): Int = {
      val b = is.read()
      if (b == -1) b else b + shift
    }
  }

  class CaesarOutputStream(os: OutputStream, shift: Int) extends OutputStream {
    override def write(c: Int): Unit = os.write(shift + c)
  }

  val origText = "abc"
  println(s"original text is $origText")

  val baos = new ByteArrayOutputStream(10)
  val cos = new CaesarOutputStream(baos, 5)
  cos.write(origText.getBytes)

  println(baos.toString)

  val bais = new ByteArrayInputStream(baos.toByteArray)
  val cis = new CaesarInputStream(bais, -5)
  val isr = new InputStreamReader(cis)
  val br = new BufferedReader(isr)

  println(br.readLine())
}