

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.formats.Cars
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class Car(regNumber: String,
               isNew: Boolean = false,
               price: Int,
               emissions: Int,
               dateOfPurchase: LocalDate) {
}

case class LEC01(cars: List[Car] = List.empty) extends CtBoxIdentifier(name = "Low emission car.")
  with CtValue[List[Car]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = cars

  override def asBoxString = Cars.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.cpQ1000(), value) match {
      case (CPQ1000(Some(false)) | CPQ1000(None), list) if list.nonEmpty => Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      case (CPQ1000(Some(true)), list) if list.isEmpty => Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
      case _ => Set.empty
    }
  }
}
