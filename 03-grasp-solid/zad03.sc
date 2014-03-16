
import scala.math.{BigDecimal => Decimal}

case class Item(price: Decimal, name: String)

val items = List(
  Item(2.79, "mydło"),
  Item(4.25, "kaszanka"),
  Item(5.99, "sledź"),
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






