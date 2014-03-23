
import scala.math.{BigDecimal => Decimal}

case class Item(price: Decimal, name: String)

val items = List(
  Item(2.79, "mydło"),
  Item(5.99, "śledź"),
  Item(4.25, "kaszanka"),
  Item(165000, "samochód")
)


object Bad {
  class TaxCalculator {
    def calculateTax(price: Decimal): Decimal = price * 0.22
  }

  class CashRegister {
    val taxCalc = new TaxCalculator

    def calculatePrice(items: Seq[Item]): Decimal =
      items
        .map(it => it.price + taxCalc.calculateTax(it.price))
        .sum

    def printBill(items: Seq[Item]): Unit =
      items.foreach { it =>
        val tax = taxCalc.calculateTax(it.price)
        println(s"towar ${it.name}: cena ${it.price} + podatek $tax")
      }
  }
}

val badRegister = new Bad.CashRegister
badRegister.calculatePrice(items)
badRegister.printBill(items)




/*
Implementacja TaxCalculator jest zafiksowana na jedną stawkę
podatku i nie bardzo daje się rozszerzać bez modyfikacji kodu
metody calculateTax. Poza tym printBill w CachRegister przyjmuje
jeden sposób drukowania paragonu. W obydwu miejscach zasada OCP
jest złamana.

Rozwiązaniem jest utworzenie dwóch interfejsów: do obliczania podatków
i do drukowania paragonów. Dopiero implementacje będą mogły specyfikować
szczegóły, co zapewni nam spełnienie zasady OCP.
 */

object Good {
  trait TaxCalculator {
    def calculateTax(price: Decimal): Decimal
  }
  class ZeroTaxCalculator extends TaxCalculator {
    def calculateTax(price: Decimal) = 0
  }
  class FixedRateTaxCalculator(rate: Decimal) extends TaxCalculator {
    def calculateTax(price: Decimal) = rate
  }
  class PercentageTaxCalculator(percentage: Int) extends TaxCalculator {
    def calculateTax(price: Decimal) = price * percentage / 100
  }

  trait BillPrinter {
    val taxCalc: TaxCalculator
    def print(items: Seq[Item]): Unit
  }
  class SimpleBillPrinter(val taxCalc: TaxCalculator) extends BillPrinter {
    def print(items: Seq[Item]): Unit =
      items.foreach { it =>
        val tax = taxCalc.calculateTax(it.price)
        println(s"towar ${it.name}: cena ${it.price} + podatek $tax")
      }
  }
  class NameSortedBillPrinter(val taxCalc: TaxCalculator) extends BillPrinter {
    def print(items: Seq[Item]): Unit =
      items.sortBy(_.name).foreach { it =>
        val tax = taxCalc.calculateTax(it.price)
        println(s"towar ${it.name}: cena ${it.price} + podatek $tax")
      }
  }

  class CashRegister {
    val taxCalc: TaxCalculator = new ZeroTaxCalculator
    val billPrinter: BillPrinter = new NameSortedBillPrinter(taxCalc)

    def calculatePrice(items: Seq[Item]): Decimal =
      items
        .map(it => it.price + taxCalc.calculateTax(it.price))
        .sum

    def printBill(items: Seq[Item]): Unit =
      billPrinter.print(items)
  }
}

val goodRegister = new Good.CashRegister
goodRegister.calculatePrice(items)
goodRegister.printBill(items)










