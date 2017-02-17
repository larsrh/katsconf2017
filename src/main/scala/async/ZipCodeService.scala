package katsconf.async

import scala.concurrent.Future

object ZipCodeService {

  sealed trait Failure
  case object IllegalZipCode extends Failure

  def citiesInZipCode(zipCode: String): Future[Either[Failure, Set[String]]] = Future.successful {
    if (zipCode matches "[0-9]{5}")
      Right(zipCode.map(n => s"City $n").toSet)
    else
      Left(IllegalZipCode)
  }

}
