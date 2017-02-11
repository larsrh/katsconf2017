package katsconf

object SalaryService {

  sealed trait Failure
  case object UnknownEmployee extends Failure
  case object AccessDenied extends Failure

  def getSalary(name: String): Either[Failure, Int] = name match {
    case "Scrooge McDuck" => Left(AccessDenied)
    case "Donald Duck"    => Right(10000)
    case "Daisy Duck"     => Right(20000)
    case _                => Left(UnknownEmployee)
  }

}
