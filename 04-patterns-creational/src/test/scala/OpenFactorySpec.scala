import org.specs2.mutable._
import OpenFactory._

class OpenFactorySpec extends Specification {

  "ShapeFactory" should {
    "register workers and create shapes" in {
      val sf = new ShapeFactory
      sf.registerWorker(new SquareWorker)
      sf.registerWorker(new RectangleWorker)
      sf.createShape("Square", 5) must beAnInstanceOf[Square]
      sf.createShape("Rectangle", 3, 8) must beAnInstanceOf[Rectangle]
    }
    "throws when suitable worker not found" in {
      val sf = new ShapeFactory
      sf.createShape("Square", 5) must throwAn[IllegalArgumentException]
    }
  }

  "SquareWorker" should {
    val sw = new SquareWorker
    "accept squares with one Int parameter" in {
      sw.accept("Square", 5) must beTrue
      sw.accept("Square") must beFalse
      sw.accept("Square", 5, 5) must beFalse
      sw.accept("SomeShape", 5) must beFalse
    }
    "create a square" in {
      sw.create(6) must beAnInstanceOf[Square]
    }
  }

  "RectangleWorker" should {
    val sw = new RectangleWorker
    "accept rectangles with two Int parameters" in {
      sw.accept("Rectangle", 5, 8) must beTrue
      sw.accept("Rectangle") must beFalse
      sw.accept("Rectangle", 5) must beFalse
      sw.accept("Rectangle", 5, 6, 7) must beFalse
      sw.accept("SomeShape", 5, 1) must beFalse
    }
    "create a rectangle" in {
      sw.create(6, 2) must beAnInstanceOf[Rectangle]
    }
  }

}
