package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.formats.Buildings
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class Building(
                     name: Option[String],
                     postcode: Option[String],
                     earliestWrittenContract: Option[LocalDate],
                     nonResidentialActivityStart: Option[LocalDate],
                     cost: Option[BigDecimal]
                   )

case class SBA01(buildings: List[Building] = List.empty) extends CtBoxIdentifier(name = "Structures and buildings allowance building")
  with CtValue[List[Building]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

    override def value = buildings

    override def asBoxString = Buildings.asBoxString(this)

    override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
  Set.empty
    }
}
