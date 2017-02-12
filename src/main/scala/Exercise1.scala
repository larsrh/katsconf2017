package katsconf

import cats.data._
import cats.implicits._

object Exercise1 {

  sealed trait Failure
  case object EmptyName extends Failure
  case class SalaryFailure(err: Option[SalaryService.Failure]) extends Failure
  case class ZipCodeFailure(err: Option[ZipCodeService.Failure]) extends Failure

  case class Employee private[Exercise1](name: String, zipCode: String, city: String, salary: Int)

  // Task 1: You are given raw employee data. If there's an error, give it back.
  // You'll need to add cases to the `Failure` trait.

  def checkName(name: String): Either[Failure, String] = {
    val trimmed = name.trim
    if (trimmed.isEmpty)
      Left(EmptyName)
    else
      Right(trimmed)
  }

  def checkCity(zipCode: String, city: String): Either[Failure, (String, String)] = ZipCodeService.citiesInZipCode(zipCode) match {
    case Left(err) => Left(ZipCodeFailure(Some(err)))
    case Right(cities) =>
      if (cities.contains(city))
        Right(zipCode -> city)
      else
        Left(ZipCodeFailure(None))
  }

  def checkSalary(salary: Int): Either[Failure, Int] =
    if (salary > 0 && salary < 100000)
      Right(salary)
    else
      Left(SalaryFailure(None))

  /**
   * @param name must be non-empty
   * @param city must be in the set of cities belonging to the `zipCode`, according to `ZipCodeService`
   * @param salary must be greater than 0 and less than 100000
   */
  def mkEmployee(name: String, zipCode: String, city: String, salary: Int): Either[Failure, Employee] =
    for {
      n <- checkName(name)
      zc <- checkCity(zipCode, city)
      (z, c) = zc
      s <- checkSalary(salary)
    } yield (Employee(n, z, c, s))

  // Task 2: You are given raw employee data. If there's at least one error, return all existing errors back.
  // Don't use Validated#andThen.

  def validateName(name: String): ValidatedNel[Failure, String] = {
    val trimmed = name.trim
    if (trimmed.isEmpty)
      Validated.invalidNel(EmptyName)
    else
      Validated.valid(trimmed)
  }

  def validateCity(zipCode: String, city: String): ValidatedNel[Failure, (String, String)] = ZipCodeService.citiesInZipCode(zipCode) match {
    case Left(err) => Validated.invalidNel(ZipCodeFailure(Some(err)))
    case Right(cities) =>
      if (cities.contains(city))
        Validated.valid(zipCode -> city)
      else
        Validated.invalidNel(ZipCodeFailure(None))
  }

  def validateSalary(salary: Int): ValidatedNel[Failure, Int] =
    if (salary > 0 && salary < 100000)
      Validated.valid(salary)
    else
      Validated.invalidNel(SalaryFailure(None))

  def mkEmployee2(name: String, zipCode: String, city: String, salary: Int): ValidatedNel[Failure, Employee] = {
    val n = validateName(name)
    val zc = validateCity(zipCode, city)
    val s = validateSalary(salary)
    (n |@| zc |@| s) map { case (n, (z, c), s) => Employee(n, z, c, s) }
  }

  // Task 3: You have to fetch the salary data from the `SalaryService`.
  // You'll need to add even more cases to the `Failure` trait.
  // Don't use Validated#andThen.

  def fetchSalary(name: String): ValidatedNel[Failure, Int] = SalaryService.getSalary(name) match {
    case Left(err) => Validated.invalidNel(SalaryFailure(Some(err)))
    case Right(salary) => Validated.valid(salary)
  }

  def mkEmployee3(name: String, zipCode: String, city: String): ValidatedNel[Failure, Employee] = {
    val n = validateName(name)
    val zc = validateCity(zipCode, city)
    val s = fetchSalary(name)
    (n |@| zc |@| s) map { case (n, (z, c), s) => Employee(n, z, c, s) }
  }

}
