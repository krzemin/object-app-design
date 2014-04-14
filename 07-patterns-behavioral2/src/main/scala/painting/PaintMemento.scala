package painting

import painting.shapes.Shape

object PaintMemento {

  trait Originator {
    def restoreMemento(m: Memento): Unit
    def applyMemento(m: Memento): Unit
  }

  trait Memento
  case class AddShape(shape: Shape) extends Memento
  case class RemoveShape(idx: Int, shape: Shape) extends Memento
  case class MoveShape(idx: Int, fromX: Int, fromY: Int, toX: Int, toY: Int) extends Memento

  class Caretaker(val originator: Originator) {

    var undoStack, redoStack: List[Memento] = Nil

    def undo() {
      undoStack match {
        case undoHead :: undoTail =>
          undoStack = undoTail
          redoStack ::= undoHead
          originator.restoreMemento(undoHead)
        case Nil => // do nothing
      }
    }

    def redo() {
      redoStack match {
        case redoHead :: redoTail =>
          redoStack = redoTail
          undoStack ::= redoHead
          originator.applyMemento(redoHead)
        case Nil => // do nothing
      }

    }

    def newState(m: Memento) {
      redoStack = Nil
      undoStack ::= m
    }

  }

}
