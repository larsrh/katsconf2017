package katsconf

import cats.implicits._
import cats.kernel.Order

import org.scalacheck._
import org.scalacheck.Prop._

object Exercise2Props extends Properties("Exercise 2") {

  implicit def arbitraryTree[A : Arbitrary : Order]: Arbitrary[Tree[A]] =
    Arbitrary(implicitly[Arbitrary[List[A]]].arbitrary.map(Tree.fromList[A]))

  property("elements/empty") = {
    Tree.empty[Int].elements == Set.empty[Int]
  }

  property("elements/insert") = forAll { (x: Int, t: Tree[Int]) =>
    t.insert(x).elements == (t.elements + x)
  }

  property("isSearchTree/empty") = {
    Tree.empty[Int].isSearchTree
  }

  property("isSearchTree/insert") = forAll { (x: Int, t: Tree[Int]) =>
    t.isSearchTree ==>
      t.insert(x).isSearchTree
  }

  property("eq/reflexive") = forAll { (t: Tree[Int]) =>
    t === t
  }

  property("eq/fromList") = forAll { (xs: List[Int]) =>
    Tree.fromList(xs) === Tree.fromList(xs.reverse)
  }

/*  property("ord/transitive") = forAll { (t: Tree[Int], u: Tree[Int], v: Tree[Int]) =>
    t < u ==> u < v ==> t < v
  }*/

}
