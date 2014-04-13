
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

  import sys.process._

  def log(message: String) {
    message #>> new java.io.File(filePath)
  }
}


object LoggerFactory {
  def getLogger(logType: LogType.LogType, arg: String = null): Logger =
    logType match {
      case LogType.None => new NullLogger
      case LogType.Console => new ConsoleLogger
      case LogType.File => new FileLogger(arg)
    }
}






