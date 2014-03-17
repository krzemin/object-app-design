
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

val rect: Bad.Rectangle = new Bad.Square()
rect.setWidth(w)
rect.setHeight(h)
println(s"prostokat o wymiarach $w na $h ma pole ${Bad.calculateArea(rect)}")


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







