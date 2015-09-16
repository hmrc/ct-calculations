package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue}
import uk.gov.hmrc.ct.computations.formats.Cars


case class Car( regNumber: String,
                isNew: Boolean = false,
                price: Int,
                emissions: Int,
                dateOfPurchase: LocalDate) {
}

case class LEC01(cars: List[Car] = List.empty) extends CtBoxIdentifier(name = "Low emission car.") with CtValue[List[Car]] {

  override def value = cars

  override def asBoxString = Cars.asBoxString(this)
}




