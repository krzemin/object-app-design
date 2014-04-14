package painting

import swing._
import event._
import painting.shapes._

object Painting extends SimpleSwingApplication with PaintMemento.Originator {

  val circle = new Button { text = "Kółko" }
  val square = new Button { text = "Kwadrat" }
  val rectangle = new Button { text = "Prostokąt" }
  val move = new Button { text = "Przesuń" }
  val remove = new Button { text = "Usuń" }
  val undo = new Button { text = "Cofnij" }
  val redo = new Button { text = "Ponów" }

  val toolbar = new BoxPanel(Orientation.Horizontal) {
    contents ++= Seq(circle, square, rectangle, move, remove, undo, redo)
  }

  object State extends Enumeration {
    type State = Value
    val Circle, Square, Rectangle, Move, Remove = Value
  }

  var state = State.Circle
  var mousePressed = false
  var mouseXY = new Point()

  var shapes: List[Shape] = List(
    new Circle(80, 80, 40),
    new painting.shapes.Rectangle(300, 20, 100, 80),
    new Square(120, 180, 120)
  )

  val panel = new Panel() {
    preferredSize = new Dimension(1200, 800)
    override def paintComponent(g: java.awt.Graphics2D) {
      g.clearRect(0, 0, size.width, size.height)
      (0 until 1200 by 40).foreach { x => g.drawLine(x, 0, x, 800) }
      (0 until 800 by 40).foreach { y => g.drawLine(0, y, 1200, y) }
      shapes.foreach(_.draw(g))
      shapes.foreach(println(_))
    }
  }

  val caretaker = new PaintMemento.Caretaker(this)

  def restoreMemento(m: PaintMemento.Memento) = {
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
    panel.repaint()
  }

  def applyMemento(m: PaintMemento.Memento) = {
    m match {
      case PaintMemento.AddShape(shape) =>
        shapes ::= shape
      case PaintMemento.RemoveShape(idx, shape) =>
        shapes = shapes.patch(idx, Nil, 1)
      case PaintMemento.MoveShape(idx, _, _, toX, toY) =>
        val shape = shapes(idx).move(toX, toY)
        shapes = shapes.updated(idx, shape)
    }
    panel.repaint()
  }

  def newState(m: PaintMemento.Memento) {
    caretaker.newState(m)
    applyMemento(m)
  }


  def top = new MainFrame {
    title = "Painting app"

    contents = new BoxPanel(Orientation.Vertical) {
      contents += toolbar
      contents += panel
    }

    toolbar.contents.foreach(button => listenTo(button))
    listenTo(panel.mouse.clicks)
    listenTo(panel.mouse.moves)

    reactions += {
      case ButtonClicked(button) if button == circle => state = State.Circle
      case ButtonClicked(button) if button == square => state = State.Square
      case ButtonClicked(button) if button == rectangle => state = State.Rectangle
      case ButtonClicked(button) if button == move => state = State.Move
      case ButtonClicked(button) if button == remove => state = State.Remove
      case ButtonClicked(button) if button == undo => caretaker.undo()
      case ButtonClicked(button) if button == redo => caretaker.redo()
      case MousePressed(_, point, _, _, _) =>
        mouseXY = point
        mousePressed = true
        state match {
          case State.Remove =>
            shapes.zipWithIndex.find {
              case (shape, idx) => shape.isPointInside(point.x, point.y)
            }.foreach {
              case (shape, idx) =>
                newState(new PaintMemento.RemoveShape(idx, shape))
            }
          case x => println("pressed at state " + state)
        }
      case MouseReleased(_, point, _, _, _) =>
        mouseXY = point
        mousePressed = false
        println("mouse released = " + point)
      case MouseMoved(_, point, _) =>
        mouseXY = point
        if(mousePressed) {
          panel.repaint()
        }
    }
  }
}
