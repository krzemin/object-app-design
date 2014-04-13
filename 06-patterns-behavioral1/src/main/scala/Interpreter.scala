object Interpreter {

  type Context = Map[String, Boolean]

  trait Expr {
    def eval(ctx: Context): Boolean
  }

  object True extends Expr {
    def eval(ctx: Context) = true
  }
  object False extends Expr {
    def eval(ctx: Context) = false
  }
  class Neg(e: Expr) extends Expr {
    def eval(ctx: Context) = !e.eval(ctx)
  }
  class Var(x: String) extends Expr {
    def eval(ctx: Context) = ctx.getOrElse(x, throw new RuntimeException)
  }
  class And(e1: Expr, e2: Expr) extends Expr {
    def eval(ctx: Context) = e1.eval(ctx) && e2.eval(ctx)
  }
  class Or(e1: Expr, e2: Expr) extends Expr {
    def eval(ctx: Context) = e1.eval(ctx) || e2.eval(ctx)
  }

}
