package katsconf

import cats.data._
import cats.implicits._

object Exercise1 {

  sealed trait Failure

  case class Employee private[Exercise1](name: String, zipCode: String, city: String, salary: Int)

  // Task 1: You are given raw employee data. If there's an error, give it back.
  // You'll need to add cases to the `Failure` trait.

  /**
   * @param name must be non-empty
   * @param city must be in the set of cities belonging to the `zipCode`, according to `ZipCodeService`
   * @param salary must be greater than 0 and less than 100000
   */
  def mkEmployee(name: String, zipCode: String, city: String, salary: Int): Either[Failure, Employee] =
    ???

  // Task 2: You are given raw employee data. If there's at least one error, return all existing errors back.
  // Don't use Validated#andThen.

  def mkEmployee2(name: String, zipCode: String, city: String, salary: Int): ValidatedNel[Failure, Employee] =
    ???

  // Task 3: You have to fetch the salary data from the `SalaryService`.
  // You'll need to add even more cases to the `Failure` trait.
  // Don't use Validated#andThen.

  def mkEmployee3(name: String, zipCode: String, city: String): ValidatedNel[Failure, Employee] =
    ???

}
