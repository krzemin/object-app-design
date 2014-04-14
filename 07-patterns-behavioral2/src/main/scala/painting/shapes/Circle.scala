package painting.shapes

class Circle(val px: Int, val py: Int, val r: Int) extends Shape {

  def draw(g: java.awt.Graphics2D) {
    g.fillOval(px - r, py - r, 2*r, 2*r)
  }

  def isPointInside(x: Int, y: Int) =
    (x - px)*(x - px) + (y - py)*(y - py) <= r * r

}
