
import java.io.{InputStream, OutputStream}

object Decorator extends App {

  class CaesarInputStream(is: InputStream, shift: Int) extends InputStream {
    override def read(): Int = is.read + shift
  }

  //class CaesarOutputStream




}