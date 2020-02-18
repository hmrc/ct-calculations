/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.CtValidation

trait SBAHelper  {

  val sba01BoxId = "SBA01"
  val descriptionId = "SBA01A"
  val firstLineOfAddressId = "SBA01B"
  val postcodeId = "SBA01C"
  val earliestWrittenContractId = "SBA01D"
  val nonResActivityId = "SBA01E"
  val costId = "SBA01F"
  val filingPeriodQuestionId = "SBA01G"
  val claimId = "SBA01H"

  val dateLowerBound = new LocalDate(2018, 10, 28)
  val exampleUpperBoundDate = new LocalDate(2019, 10, 28)

  val greaterThanMaxClaimError = Set(CtValidation(Some(s"$claimId.building0"), s"error.$claimId.greaterThanMax" ,None))
}
