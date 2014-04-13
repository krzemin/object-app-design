import org.specs2.mutable._
import Interpreter._

class InterpreterSpec extends Specification {

  val ctx: Context = Map.empty

  "an interpreter" should {
    "evaluate true and false" in {
      True.eval(ctx) must beTrue
      False.eval(ctx) must beFalse
    }
    "evaluate negation" in {
      new Neg(True).eval(ctx) must beFalse
      new Neg(False).eval(ctx) must beTrue
    }
    "evaluate variables" in {
      val ctx1: Context = ctx + ("x" -> true)
      new Var("x").eval(ctx1) must beTrue
      new Var("y").eval(ctx1) must throwA[RuntimeException]
    }
  }

}
