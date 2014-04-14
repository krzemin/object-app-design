package painting.shapes

trait Shape {
  val px, py: Int
  def draw(g: java.awt.Graphics2D): Unit
  def isPointInside(x: Int, y: Int): Boolean
}
