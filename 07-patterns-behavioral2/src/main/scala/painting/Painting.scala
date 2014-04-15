package painting

import swing._
import event._
import java.awt.Color

object Painting extends SimpleSwingApplication with CanvasOriginator {

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
  var pressXY = new Point()
  var mouseXY = new Point()
  var movingIdx = -1

  val panel = new Panel() {
    preferredSize = new Dimension(1200, 800)
    override def paintComponent(g: java.awt.Graphics2D) {
      g.clearRect(0, 0, size.width, size.height)
      g.setColor(Color.GRAY)
      (0 until 1200 by 40).foreach { x => g.drawLine(x, 0, x, 800) }
      (0 until 800 by 40).foreach { y => g.drawLine(0, y, 1200, y) }
      g.setColor(Color.BLUE)
      shapes.foreach(_.draw(g))
    }
  }

  val caretaker = new PaintMemento.Caretaker(this)

  def newState(m: PaintMemento.Memento) {
    caretaker.newState(m)
    applyMemento(m)
  }

  def repaint() {
    panel.repaint()
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
        mouseXY = point; pressXY = point;  mousePressed = true
        state match {
          case State.Move => Events.startMoving(point).foreach(movingIdx = _)
          case State.Remove => Events.remove(point)
          case _ => // do nothing
        }
      case MouseReleased(_, point, _, _, _) =>
        mouseXY = point; mousePressed = false
        state match {
          case State.Circle => Events.circle(pressXY, point)
          case State.Square => Events.square(pressXY, point)
          case State.Rectangle => Events.rectangle(pressXY, point)
          case State.Move =>
            if(movingIdx != -1) {
              Events.finishMoving(movingIdx, pressXY, point)
              movingIdx = -1
            }
          case _ => // do nothing
        }
      case MouseMoved(_, point, _) =>
        mouseXY = point
        if(mousePressed && movingIdx != -1) {
          Events.continueMoving(movingIdx, point)
        }
    }
  }
}
