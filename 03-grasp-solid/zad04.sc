
object Bad {

  class Rectangle
  {
    protected var width: Int = _
    protected var height: Int = _

    def getWidth = width
    def setWidth(w: Int) = { width = w }

    def getHeight = height
    def setHeight(h: Int) = { height = h }
  }

  class Square extends Rectangle {
    override def setWidth(w: Int) = {
      width = w
      height = w
    }

    override def setHeight(h: Int) = {
      width = h
      height = h
    }
  }

  def calculateArea(rect: Rectangle) =
    rect.getWidth * rect.getHeight
}

val (w, h) = (4, 5)
val rect1: Bad.Rectangle = new Bad.Square()
rect1.setWidth(w)
rect1.setHeight(h)
println(s"prostokat o wymiarach $w na $h ma pole ${Bad.calculateArea(rect1)}")


/*
Wyszło śmiesznie, bo prostokąt 4x5 nie ma pola 25. W rzeczywistym świecie
rzeczywiście, kwadrat jest prostokątem, natomiast w modelu obiektowym
relacje z rzeczywistego świata nie zawsze są zachowane. W tym przypadku
powodem utraty takiej relacji jest mutowalność klasy Rectangle.

Rozwiązania są dwa: jeśli koniecznie chcemy (bądź musimy) zachować mutowalność,
to rezygnujemy z dziedziczenia na rzecz braku relacji podtypowania pomiędzy
klasami. Wówczas Square ma inny interfejs i nie zrobimy sobie krzywdy
w sposób, jak powyżej.

Inne rozwiązanie polega na uczynieniu klasy Rectangle niemutowalną, wówczas
bez problemu Square może po niej dziedziczyć.
 */

object Good {
  class Rectangle(val width: Int, val height: Int)

  class Square(side: Int) extends Rectangle(width = side, height = side)

  def calculateArea(rect: Rectangle) =
    rect.width * rect.height
}


val side = 5
val rect2: Good.Rectangle = new Good.Square(side)
val rect3: Good.Rectangle = new Good.Rectangle(w, h)

println(s"prostokat o wymiarach ${rect2.width} na ${rect2.height} ma pole ${Good.calculateArea(rect2)}")
println(s"prostokat o wymiarach ${rect3.width} na ${rect3.height} ma pole ${Good.calculateArea(rect3)}")

/*
Ponieważ klasy Good nie są mutowalne, to interfejs zabrania nam modyfikowania
tylko jednej współrzędnej prostokąta w istniejącym obiekcie.
 */






