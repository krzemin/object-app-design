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
      val ctx1: Context = ctx + ("x" -> true, "y" -> false)
      new Var("x").eval(ctx1) must beTrue
      new Var("y").eval(ctx1) must beFalse
      new Var("z").eval(ctx1) must throwA[RuntimeException]
    }
    "evaluate conjunction" in {
      new And(True, True).eval(ctx) must beTrue
      new And(True, False).eval(ctx) must beFalse
      new And(False, True).eval(ctx) must beFalse
      new And(False, new Var("y")).eval(ctx) must beFalse
    }
    "evaluate disjunction" in {
      new Or(True, True).eval(ctx) must beTrue
      new Or(False, False).eval(ctx) must beFalse
      new Or(False, True).eval(ctx) must beTrue
      new Or(True, new Var("y")).eval(ctx) must beTrue
    }
    "evaluate complex expressions" in {
      val ctx1: Context = ctx + ("x" -> false, "y" -> true)
      val e1 = new And(new Var("y"), new Or(new Neg(new Var("y")), True))
      e1.eval(ctx1) must beTrue
    }
  }

}
