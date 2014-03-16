trait DataProvider {
  def getData: String
}

trait ReportFormatter {
  def format(data: String): String
}

trait ReportPrinter {
  def print(report: String): Unit
}

class StringDataProvider extends DataProvider {
  def getData = "this is data"
}

class StringDocumentFormatter extends ReportFormatter {
  def format(data: String) = s"<<< $data >>>"
}

class StdOutReportPrinter extends ReportPrinter {
  def print(report: String) = println(report)
}

class ReportComposer(dataProvider: DataProvider,
                     reportFormatter: ReportFormatter,
                     reportPrinter: ReportPrinter) {

  def compose() {
    val data = dataProvider.getData
    val report = reportFormatter.format(data)
    reportPrinter.print(report)
  }
}

val reportComposer = new ReportComposer(new StringDataProvider,
                                        new StringDocumentFormatter,
                                        new StdOutReportPrinter)
reportComposer.compose()


