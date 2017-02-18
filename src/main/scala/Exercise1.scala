package katsconf

import cats.data._
import cats.implicits._
import cats.syntax.CartesianBuilder

object Exercise1 {

  sealed trait Failure
  case class EmptyString(fieldName:String) extends Failure
  case class InvalidCity(city:String) extends Failure
  case class ValueOutOfRange(i:Int) extends Failure
  final val zip = List("00111", "001122", "000333")
  final val cities = List("Dublin", "London", "Madrid")

  case class Employee private[Exercise1](name: String, zipCode: String, city: String, salary: Int)

  // Task 1: You are given raw employee data. If there's an error, give it back.
  // You'll need to add cases to the `Failure` trait.

  /**
   * @param name must be non-empty
   * @param city must be in the set of cities belonging to the `zipCode`, according to `ZipCodeService`
   * @param salary must be greater than 0 and less than 100000
   */
  def mkEmployee(name: String, zipCode: String, city: String, salary: Int): Either[Failure, Employee] =
    for {
      n <- nonBlank("name")(name)
      z <- validZip(zipCode)
      c <- validCity(city)
      s <- inRange("salary", 0, 100000)(salary)
    } yield Employee(n, z, c, s)



  // Task 2: You are given raw employee data. If there's at least one error, return all existing errors back.
  // Don't use Validated#andThen.

  def mkEmployee2(name: String, zipCode: String, city: String, salary: Int): ValidatedNel[Failure, Employee] =
    (nonBlank("name")(name).toValidatedNel |@|
      validZip(zipCode).toValidatedNel |@|
      validCity(city).toValidatedNel |@|
      inRange("salary", 0, 100000)(salary).toValidatedNel).map(Employee)

  // Task 3: You have to fetch the salary data from the `SalaryService`.
  // You'll need to add even more cases to the `Failure` trait.
  // Don't use Validated#andThen.

  def mkEmployee3(name: String, zipCode: String, city: String): ValidatedNel[Failure, Employee] =
    ???



  def nonBlank(name: String)(data:String): Failure Either String =
    Either.right(data).
      ensure(EmptyString(s"$name cannot be blank"))(_.nonEmpty)

  def inRange(name: String, lower: Int, upper: Int)(data:Int): Either[Failure, Int] =
    Either.right(data).
      ensure(ValueOutOfRange(data))(i => i >=lower && i <=upper)

  def validZip(data: String): Failure Either String =
    Either.right(data).
      ensure(InvalidCity(data))(zip contains _)

  def validCity(data: String): Failure Either String =
    Either.right(data).
      ensure(InvalidCity(data))(cities contains _)

}
