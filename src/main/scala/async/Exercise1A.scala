package katsconf.async

import cats.data._
import cats.implicits._

import scala.concurrent.Future

object Exercise1A {

  // Task 1: Copy your definition of `Failure` from `Exercise1`.

  sealed trait Failure

  case class Employee private[Exercise1A](name: String, zipCode: String, city: String, salary: Int)

  // Task 2: Fetch the data as previously. Don't use nested for-comprehensions.

  def mkEmployeeAsync(name: String, zipCode: String, city: String): Future[Either[Failure, Employee]] =
    ???

}
