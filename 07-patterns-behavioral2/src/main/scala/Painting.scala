import swing._
import event._

object Painting extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Painting app"

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

    val panel = new Panel() {
      preferredSize = new Dimension(800, 600)
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += toolbar
      contents += panel
    }

    toolbar.contents.foreach(button => listenTo(button))
    listenTo(panel.mouse.clicks)

    reactions += {
      case ButtonClicked(button) if button == circle =>
        println("circle clicked")
      case ButtonClicked(button) if button == square =>
        println("square clicked")
      case ButtonClicked(button) if button == rectangle =>
        println("rectangle clicked")
      case ButtonClicked(button) if button == move =>
        println("move clicked")
      case ButtonClicked(button) if button == remove =>
        println("remove clicked")
      case ButtonClicked(button) if button == undo =>
        println("undo clicked")
      case ButtonClicked(button) if button == redo =>
        println("redo clicked")
      case MousePressed(_, point, _, _, _) =>
        println("mouse pressed = " + point)
      case MouseReleased(_, point, _, _, _) =>
        println("mouse released = " + point)
    }
  }
}
