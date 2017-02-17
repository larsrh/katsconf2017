package katsconf

object ZipCodeService {

  sealed trait Failure
  case object IllegalZipCode extends Failure

  def citiesInZipCode(zipCode: String): Either[Failure, Set[String]] =
    if (zipCode matches "[0-9]{5}")
      Right(zipCode.map(n => s"City $n").toSet)
    else
      Left(IllegalZipCode)

}
