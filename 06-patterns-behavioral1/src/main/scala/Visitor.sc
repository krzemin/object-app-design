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

val t1: Tree = new Node(
  new Node(new Leaf, new Node(new Leaf, new Leaf)),
  new Node(new Node(new Leaf, new Leaf), new Leaf))

val lcv = new LeafCountVisitor
t1.accept(lcv)
println(s"t1 has ${lcv.leafs} leafs")





