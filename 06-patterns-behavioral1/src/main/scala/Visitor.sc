trait Tree {
  def accept(visitor: Visitor)
}

class Leaf extends Tree {
  def accept(visitor: Visitor) {
    visitor.visitLeaf(this)
  }
}
class Node(left: Tree, right: Tree) extends Tree {
  def accept(visitor: Visitor) {
    visitor.visitNode(this)
  }
}

trait Visitor {
  def visitLeaf(leaf: Leaf)
  def visitNode(node: Node)
}


val t1: Tree = new Node(
  new Node(new Leaf, new Node(new Leaf, new Leaf)),
  new Node(new Node(new Leaf, new Leaf), new Leaf))



