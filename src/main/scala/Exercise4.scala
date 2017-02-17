package katsconf

import cats.kernel._
import cats.implicits._

object Exercise4 {

  case class Matrix[A](entries: List[List[A]]) {
    val size = entries.length

    require(entries.forall(_.length == size))

    // Task 1: Implement matrix addition.
    // (Assume that both matrices have the same size)

    def +(that: Matrix[A])(implicit sg: Semigroup[A]): Matrix[A] = {
      require(this.size == that.size)
      ???
    }

    // Task 2: Implement scalar multiplication.
    // (Multiply each entry with a given scalar value)

    def scale(r: A)(implicit sg: Semigroup[A]): Matrix[A] =
      ???
  }

  // Task 3: Notice anything fishy?
  // Look at the way the operations from the `Semigroup` are used.
  //
  // Hints:
  //   - How would you implement the unit matrix?
  //
  //     (1 0 0 0)
  //     (0 1 0 0)
  //     (0 0 1 0)
  //     (0 0 0 1)
  //
  //   - Can you implement matrix multiplication?

}
