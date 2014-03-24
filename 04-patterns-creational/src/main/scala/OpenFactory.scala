object OpenFactory {

  trait Shape

  trait ShapeFactoryWorker {
    def accept(shape: String, params: Any*): Boolean
    def create(params: Any*): Shape
  }

  class ShapeFactory {
    private var workers = List.empty[ShapeFactoryWorker]
    def registerWorker(worker: ShapeFactoryWorker) {
      workers ::= worker
    }
    def createShape(shape: String, params: Any*): Shape = {
      workers.find(worker => worker.accept(shape, params: _*)) match {
        case None => throw new IllegalArgumentException
        case Some(worker) => worker.create(params: _*)
      }
    }
  }

  class Square(val a: Int) extends Shape
  class Rectangle(val a: Int, b: Int) extends Shape

  class SquareWorker extends ShapeFactoryWorker {
    def create(params: Any*): Shape =
      new Square(params(0).asInstanceOf[Int])

    def accept(shape: String, params: Any*): Boolean =
      shape == "Square" &&
        params.size == 1 &&
        params(0).isInstanceOf[Int]
  }

  class RectangleWorker extends ShapeFactoryWorker {
    def create(params: Any*): Shape =
      new Rectangle(params(0).asInstanceOf[Int],
        params(1).asInstanceOf[Int])

    def accept(shape: String, params: Any*): Boolean =
      shape == "Rectangle" &&
        params.size == 2 &&
        params(0).isInstanceOf[Int] &&
        params(1).isInstanceOf[Int]
  }

}
