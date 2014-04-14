package painting

import painting.shapes.Shape

object PaintMemento {

  trait Originator {
    def restoreMemento(m: Memento): Unit
    def applyMemento(m: Memento): Unit
    def newState(m: Memento) {

    }
  }

  trait Memento
  class AddShape(val shape: Shape) extends Memento
  class RemoveShape(val idx: Int, val shape: Shape) extends Memento
  class MoveShape(val idx: Int, val lastX: Int, val lastY: Int) extends Memento

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
