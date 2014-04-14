package painting


import painting.shapes._

trait CanvasOriginator extends PaintMemento.Originator {
  self : { def newState(m:PaintMemento.Memento): Unit
           def repaint(): Unit } =>

  var shapes: List[Shape] = List(
    new Circle(80, 80, 40),
    new painting.shapes.Rectangle(300, 20, 100, 80),
    new Square(120, 180, 120)
  )

  def restoreMemento(m: PaintMemento.Memento) {
    m match {
      case PaintMemento.AddShape(shape) =>
        shapes ::= shape
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
