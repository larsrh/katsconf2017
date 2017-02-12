package katsconf

import cats.implicits._
import cats.kernel._

sealed trait Tree[A] {

  // Task 1: Write an insertion function that adds an element in the right spot.

  def insert(a: A)(implicit ord: Order[A]): Tree[A] = this match {
    case Leaf() => Node(Leaf(), a, Leaf())
    case Node(left, value, right) if a < value => Node(left.insert(a), value, right)
    case Node(left, value, right) => Node(left, value, right.insert(a))
  }

  // Task 2: Write a function that converts this tree to a set

  def elements: Set[A] = this match {
    case Leaf() => Set.empty
    case Node(left, value, right) => left.elements ++ right.elements + value
  }

  // Task 3: Write a function that checks whether this tree satisfies the
  // "search tree property".

  def isSearchTree(implicit ord: Order[A]): Boolean = this match {
    case Leaf() => true
    case Node(left, value, right) =>
      left.isSearchTree && right.isSearchTree &&
        left.elements.forall(_ < value) &&
        right.elements.forall(_ >= value)
  }

}

object Tree {
  def empty[A]: Tree[A] = Leaf[A]()
  def fromList[A : Order](xs: List[A]): Tree[A] = xs.foldLeft(empty[A])((t, a) => t.insert(a))

  // Task 4: Write an equality check for trees that ignores structure

  implicit def treeEq[A : Eq]: Eq[Tree[A]] = new Eq[Tree[A]] {
    def eqv(t: Tree[A], u: Tree[A]) = Eq[Set[A]].eqv(t.elements, u.elements)
  }

  // Task 5: (advanced!) implement tree ordering
  // Hint: search the web for "multiset order"
/*
  implicit def treeOrder[A : Order]: Order[Tree[A]] = new Order[Tree[A]] {
    def compare(t: Tree[A], u: Tree[A]) = ???
  }*/

}

case class Leaf[A]() extends Tree[A]
case class Node[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]
