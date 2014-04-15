package painting


import painting.shapes._

trait CanvasOriginator extends PaintMemento.Originator {
  self : { def newState(m:PaintMemento.Memento): Unit
           def repaint(): Unit } =>

  var shapes: List[Shape] = Nil

  def restoreMemento(m: PaintMemento.Memento) {
    m match {
      case PaintMemento.AddShape(shape) =>
        shapes = shapes.filterNot(_ == shape)
      case PaintMemento.RemoveShape(idx, shape) =>
        shapes.splitAt(idx) match {
          case (left, right) => shapes = left ++ (shape :: right)
        }
      case PaintMemento.MoveShape(idx, lastX, lastY, _, _) =>
        val shape = shapes(idx).move(lastX, lastY)
        shapes = shapes.updated(idx, shape)
    }
    repaint()
  }

  def applyMemento(m: PaintMemento.Memento) {
    m match {
      case PaintMemento.AddShape(shape) =>
        shapes ::= shape
      case PaintMemento.RemoveShape(idx, shape) =>
        shapes = shapes.patch(idx, Nil, 1)
      case PaintMemento.MoveShape(idx, _, _, toX, toY) =>
        val shape = shapes(idx).move(toX, toY)
        shapes = shapes.updated(idx, shape)
    }
    repaint()
  }

  object Events {

    def circle(pointFrom: java.awt.Point, pointTo: java.awt.Point) {
      val shape = new Circle(pointFrom.x, pointFrom.y,
        pointTo.distance(pointFrom).asInstanceOf[Int])
      newState(new PaintMemento.AddShape(shape))
    }

    def square(pointFrom: java.awt.Point, pointTo: java.awt.Point) {
      val shape = new Square(pointFrom.x, pointFrom.y,
        pointTo.distance(pointFrom).asInstanceOf[Int])
      newState(new PaintMemento.AddShape(shape))
    }

    def rectangle(pointFrom: java.awt.Point, pointTo: java.awt.Point) {
      val (miX, miY) = (math.min(pointFrom.x, pointTo.x), math.min(pointFrom.y, pointTo.y))
      val (maX, maY) = (math.max(pointFrom.x, pointTo.x), math.max(pointFrom.y, pointTo.y))
      val shape = new Rectangle(miX, miY, maX - miX, maY - miY)
      newState(new PaintMemento.AddShape(shape))
    }

    def startMoving(point: java.awt.Point): Option[Int] = {
      shapes.zipWithIndex.find {
        case (shape, idx) => shape.isPointInside(point.x, point.y)
      }.map(_._2)
    }

    def continueMoving(idx: Int, point: java.awt.Point) {
      shapes = shapes.patch(idx, List(shapes(0).move(point.x, point.y)), 1)
      repaint()
    }

    def finishMoving(idx: Int, pointFrom: java.awt.Point, pointTo: java.awt.Point) {
      newState(new PaintMemento.MoveShape(idx,
        pointFrom.x, pointFrom.y,
        pointTo.x, pointTo.y)
      )
    }

    def remove(point: java.awt.Point) {
      shapes.zipWithIndex.find {
        case (shape, idx) => shape.isPointInside(point.x, point.y)
      }.foreach {
        case (shape, idx) =>
          newState(new PaintMemento.RemoveShape(idx, shape))
      }
    }
  }

}
