package painting.shapes

case class Rectangle(val px: Int, val py: Int, val w: Int, val h: Int)
  extends Shape {

  def draw(g: java.awt.Graphics2D) {
    g.fillRect(px, py, w, h)
  }

  def isPointInside(x: Int, y: Int) =
    x >= px && y >= py && x <= px + w && y <= py + h

  def move(x: Int, y: Int) = copy(px = x, py = y)
}
