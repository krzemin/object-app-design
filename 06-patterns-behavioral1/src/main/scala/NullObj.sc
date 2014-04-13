import java.io.FileWriter
trait Logger {
  def log(message: String)
}
object LogType extends Enumeration {
  type LogType = Value
  val None, Console, File = Value
}

class NullLogger extends Logger {
  def log(message: String) {}
}
class ConsoleLogger extends Logger {
  def log(message: String) {
    println(message)
  }
}
class FileLogger(val filePath: String) extends Logger {

  def log(message: String) {
    val fw = new FileWriter(filePath, true)
    fw.append(message)
    fw.append('\n')
    fw.close()
  }
}

object LoggerFactory {
  def getLogger(logType: LogType.LogType, arg: String = null): Logger =
    logType match {
      case LogType.None => new NullLogger
      case LogType.Console => new ConsoleLogger
      case LogType.File =>
        if(arg != null) new FileLogger(arg)
        else new NullLogger
    }
}
val log1 = LoggerFactory.getLogger(LogType.None)
log1.log("some call which is not logged")
val log2 = LoggerFactory.getLogger(LogType.Console)
log2.log("logging to the console")

val log3 = LoggerFactory.getLogger(LogType.File, "/tmp/log.txt")
log3.log("logging to a file")







