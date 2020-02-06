package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.CtValidation

trait SBAHelper  {

  val sba01BoxId = "SBA01"
  val nameId = "SBA01A"
  val postcodeId = "SBA01B"
  val earliestWrittenContractId = "SBA01C"
  val nonResActivityId = "SBA01D"
  val costId = "SBA01E"
  val claimId = "SBA01F"

  val dateLowerBound = new LocalDate(2018, 10, 28)
  val exampleUpperBoundDate = new LocalDate(2019, 10, 28)
}
