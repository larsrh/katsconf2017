package katsconf.async

import scala.concurrent.Future

object SalaryService {

  sealed trait Failure
  case object UnknownEmployee extends Failure
  case object AccessDenied extends Failure

  def getSalary(name: String): Future[Either[Failure, Int]] = Future.successful(name match {
    case "Scrooge McDuck" => Left(AccessDenied)
    case "Donald Duck"    => Right(10000)
    case "Daisy Duck"     => Right(20000)
    case _                => Left(UnknownEmployee)
  })

}
