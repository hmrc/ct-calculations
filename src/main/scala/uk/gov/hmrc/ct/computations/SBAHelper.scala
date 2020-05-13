

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate

trait SBAHelper  {

  val sba01BoxId = "SBA01"
  val descriptionId = "SBA01A"
  val firstLineOfAddressId = "SBA01B"
  val postcodeId = "SBA01C"
  val earliestWrittenContractId = "SBA01D"
  val nonResActivityId = "SBA01E"
  val costId = "SBA01F"
  val filingPeriodQuestionId = "SBA01G"
  val broughtForwardId = "SBA01H"
  val claimId = "SBA01I"
  val carriedForwardId = "SBA01J"
  val claimNoteId = "SBA01K"

  val dateLowerBound = new LocalDate(2018, 10, 29)
  val exampleUpperBoundDate = new LocalDate(2019, 10, 28)
}
