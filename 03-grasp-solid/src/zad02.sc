
object Bad {
  trait ReportPrinter {
    def getData: String
    def formatDocument(): Unit
    def printReport(): Unit
  }
  class ReportPrinterImpl extends ReportPrinter {
    var data: String = _
    def getData = { data = "this is data"; data }
    def formatDocument() = { data = s"<<< $data >>>" }
    def printReport() = println(data)
  }
}
val badReportPrinter = new Bad.ReportPrinterImpl
badReportPrinter.getData
badReportPrinter.formatDocument()
badReportPrinter.printReport()



/*
Problem z tą implementacją jest taki, że ReportPrinter
ma 3 odpowiedzialności: pobieranie danych, formatowanie
raportu, drukowanie raportu. Nijak nie spełnia więc SRP.
Rozwiązaniem jest odseparowanie tych odpowiedzialności
do osobnych interfejsów.
Poza tym, implementacja ReportPrinterImpl musi niejako
przechowywać stan (data) pomiędzy wywołaniami
getData/formatDocument/printReport, co zostało wymuszone
przez koślawy interfejs ReportPrinter
*/

object Good {
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
}

val goodDataProvider = new Good.StringDataProvider
val goodReportFormatter = new Good.StringDocumentFormatter
val goodReportPrinter = new Good.StdOutReportPrinter
val data = goodDataProvider.getData
val report = goodReportFormatter.format(data)
goodReportPrinter.print(report)












