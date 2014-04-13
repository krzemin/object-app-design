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


}
