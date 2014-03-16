
trait ReportPrinter {
  def getData: String
  def formatDocument: Unit
  def printReport: Unit
}
class ReportPrinterImpl extends ReportPrinter {
  var data: String = _
  def getData = { data = "this is data"; data }
  def formatDocument = { data = s"<<< $data >>>" }
  def printReport = println(data)
}

val reportPrinter = new ReportPrinterImpl
reportPrinter.getData
reportPrinter.formatDocument
reportPrinter.printReport
