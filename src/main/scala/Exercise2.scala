package katsconf

import cats.implicits._
import cats.kernel._

sealed trait Tree[A] {

  // Task 1: Write an insertion function that adds an element in the right spot.

  def insert(a: A)(implicit ord: Order[A]): Tree[A] = ???

  // Task 2: Write a function that converts this tree to a set

  def elements: Set[A] = ???

  // Task 3: Write a function that checks whether this tree satisfies the
  // "search tree property".

  def isSearchTree: Boolean = ???

}

object Tree {
  def empty[A]: Tree[A] = Leaf[A]()
  def fromList[A : Order](xs: List[A]): Tree[A] = xs.foldLeft(empty[A])((t, a) => t.insert(a))

  // Task 4: Write an equality check for trees that ignores structure

  implicit def treeEq[A : Eq]: Eq[Tree[A]] = new Eq[Tree[A]] {
    def eqv(t: Tree[A], u: Tree[A]) = ???
  }

  // Task 5: (advanced!) implement tree ordering
  // Hint: search the web for "multiset order"

  implicit def treeOrder[A : Order]: Order[Tree[A]] = new Order[Tree[A]] {
    def compare(t: Tree[A], u: Tree[A]) = ???
  }

}

case class Leaf[A]() extends Tree[A]
case class Node[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]
