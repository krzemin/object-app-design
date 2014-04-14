import swing._
import event._

object Painting extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Painting app"

    val b1 = new Button { text = "kaka" }

    contents = new BoxPanel(Orientation.Horizontal) {
      contents += b1
      contents += b1
    }

  }
}
