package katsconf

import algebra.ring._
import cats.kernel._
import cats.implicits._

object Exercise4 {

  object Matrix {

    def randomIntMatrix(size: Int): Matrix[Int] =
      Matrix(List.fill(size)(List.fill(size)(scala.util.Random.nextInt(20) - 10)))

    def unitMatrix[A](size: Int)(implicit r: Rig[A]): Matrix[A] =
      Matrix(
        (for (i <- 0 until size)
        yield
          (for (j <- 0 until size)
          yield
            if (i == j)
              r.one
            else
              r.zero).toList).toList
      )

  }

  case class Matrix[A](entries: List[List[A]]) {
    val size = entries.length

    require(entries.forall(_.length == size))

    override def toString: String =
      entries.map(row =>
        row.mkString("(", " ", ")")
      ).mkString("\n")

    // Task 1: Implement matrix addition.
    // (Assume that both matrices have the same size)

    def +(that: Matrix[A])(implicit sg: Semigroup[A]): Matrix[A] = {
      require(this.size == that.size)
      Matrix(
        for {
          (row1, row2) <- (this.entries zip that.entries)
        } yield (row1 zip row2) map { case (e1, e2) => e1 |+| e2 }
      )
    }

    // Task 2: Implement scalar multiplication.
    // (Multiply each entry with a given scalar value)

    def scale(r: A)(implicit sr: Semiring[A]): Matrix[A] =
      Matrix(
        for (row <- entries)
        yield
          for (item <- row) yield sr.times(r, item)
      )

    def *(that: Matrix[A])(implicit sr: Semiring[A]): Matrix[A] = {
      require(this.size == that.size)
      // There are multiple ways to implement this. I find this one pretty
      // elegant, although it might not be very efficient.

      // This turns a matrix
      //   (a b c)
      //   (d e f)
      //   (g h i)
      // into
      //   (a d g)
      //   (b e h)
      //   (c f i)
      // That way, we can iterate over the *columns* of the second matrix.
      val transposed = that.entries.transpose

      val res =
        // for each row in the first matrix ...
        for (row <- this.entries) yield
          // ... and for each column in the second matrix ...
          for (col <- transposed) yield {
            // ... compute an entry in the result.

            val zipped = row zip col
            // here, zipped contains (a, b) pairs which need to be multiplied
            val multiplied =
              for ((x, y) <- zipped) yield
                sr.times(x, y)
            // now we just have to add those up
            multiplied.foldLeft(sr.zero)(sr.plus)
          }

      Matrix(res)
    }
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
