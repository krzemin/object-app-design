import org.joda.time._
import java.io._

object HourlyLimitedAccessProxy extends App {

  trait HourlyLimitedAccess {
    val availableFromHour: Int
    val availableToHour: Int

    def isAvailable: Boolean =
      (availableFromHour to availableToHour) contains DateTime.now.getHourOfDay

    object NotAvailableException extends Throwable
  }

  class HourlyLimitedOutputStream(os: OutputStream,
                                  val availableFromHour: Int,
                                  val availableToHour: Int)
    extends OutputStream with HourlyLimitedAccess {

    override def write(c: Int): Unit = {
      if(isAvailable) os.write(c) else throw NotAvailableException
    }
  }

  class HourlyLimitedInputStream(is: InputStream,
                                 val availableFromHour: Int,
                                 val availableToHour: Int)
    extends InputStream with HourlyLimitedAccess {



    override def read(): Int = {
      if(isAvailable) is.read() else throw NotAvailableException
    }
  }


  val origText = "abc"
  println(s"original text is $origText")

  val baos = new ByteArrayOutputStream(10)
  val cos = new Decorator.CaesarOutputStream(baos, 5)
  val hlos = new HourlyLimitedOutputStream(cos, 8, 22)
  val los = new LoggingProxy.LoggingOutputStream(hlos)
  los.write(origText.getBytes)

  println(baos.toString)

  val bais = new ByteArrayInputStream(baos.toByteArray)
  val cis = new Decorator.CaesarInputStream(bais, -5)
  val hlis = new HourlyLimitedInputStream(cis, 8, 22)
  val lis = new LoggingProxy.LoggingInputStream(hlis)
  val isr = new InputStreamReader(lis)
  val br = new BufferedReader(isr)

  println(br.readLine())


}
