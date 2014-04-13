trait Tree {
  def accept(visitor: Visitor)
}

class Leaf extends Tree {
  def accept(visitor: Visitor) {
    visitor.visitLeaf(this)
  }
}
class Node(val left: Tree, val right: Tree) extends Tree {
  def accept(visitor: Visitor) {
    visitor.visitNode(this)
  }
}

trait Visitor {
  def visitLeaf(leaf: Leaf)
  def visitNode(node: Node)
}

class LeafCountVisitor extends Visitor {
  var leafs = 0

  def visitNode(node: Node) {
    node.left.accept(this)
    node.right.accept(this)
  }

  def visitLeaf(leaf: Leaf) {
    leafs += 1
  }
}

class DepthCountVisitor extends Visitor {
  var maxDepth = 0
  private var currDepth = 0

  def visitNode(node: Node) {
    currDepth += 1
    node.left.accept(this)
    node.right.accept(this)
    currDepth -= 1
  }

  def visitLeaf(leaf: Leaf) {
    maxDepth = math.max(maxDepth, currDepth)
  }
}


def go(t: Tree, name: String) {
  val lcv = new LeafCountVisitor
  t.accept(lcv)
  println(s"$name has ${lcv.leafs} leafs")
  val dcv = new DepthCountVisitor
  t.accept(dcv)
  println(s"$name has height ${dcv.maxDepth}")
}
val t0 = new Leaf
val t1 = new Node(new Leaf, new Leaf)
val t2 = new Node(new Leaf, new Node(new Leaf, new Leaf))
val t3 = new Node(
  new Node(new Leaf, new Node(new Leaf, new Leaf)),
  new Node(new Node(new Leaf, new Leaf), new Leaf))
val t4 = new Node(t1, t1)
val t5 = new Node(t4, t4)

List(t0, t1, t2, t3, t4, t5)
  .zipWithIndex
  .foreach { case (t, i) =>
  go(t, s"t$i")
}









