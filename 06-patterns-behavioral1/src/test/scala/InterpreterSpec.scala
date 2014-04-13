import org.specs2.mutable._
import Interpreter._

class InterpreterSpec extends Specification {

  var ctx: Context = Map.empty

  "an interpreter" should {
    "evaluate true and false" in {
      True.eval(ctx) must beTrue
      False.eval(ctx) must beFalse
    }
  }

}
