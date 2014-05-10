import scala.util.{Try, Success, Failure}

trait Command {
  def execute(args: List[String]): Try[Unit]
}

object FtpReceiver {
  def receiveFile(url: String, name: String) {
    println(s"ftp client receiving file $url to $name...")
    Thread.sleep(5000)
    println(s"file $url received to $name")
  }
}

class FtpCommand(val name: String) extends Command {
  def execute(args: List[String]) = args match {
    case List(url) => Try(FtpReceiver.receiveFile(url, name))
    case _ => Failure(new Throwable("need one argument - ftp file url"))
  }
}

object HttpReceiver {
  def receiveFile(url: String, name: String) {
    println(s"http client receiving file $url to $name...")
    Thread.sleep(400)
    println(s"file $url received to $name")
  }
}

class HttpCommand(val name: String) extends Command {
  def execute(args: List[String]) = args match {
    case List(url) => Try(HttpReceiver.receiveFile(url, name))
    case _ => Failure(new Throwable("need one argument - http file url"))
  }
}

object RandomFileReceiver {
  def fillRandomFile(name: String, size: Int) {
    println(s"filling $name with random content of size $size")
    Thread.sleep(60)
    println(s"done filling $name")
  }
}

class RandomFileCommand(val size: Int) extends Command {
  def execute(args: List[String]) = args match {
    case List(name) => Try(RandomFileReceiver.fillRandomFile(name, size))
    case _ => Failure(new Throwable("need one argument - file name"))
  }
}


val ftpCmd = new FtpCommand("/tmp/localfile")
val result1 = ftpCmd.execute(List("ftp://example.org/remotefile"))

val httpCmd = new HttpCommand("/tmp/localfile2")
val result2 = httpCmd.execute(List("http://example.org/remotefile2"))

val rfCmd = new RandomFileCommand(2048)
val result3 = rfCmd.execute(List("/tmp/localfile3"))










