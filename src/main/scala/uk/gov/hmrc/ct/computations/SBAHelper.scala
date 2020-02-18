/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
  val claimId = "SBA01G"
  val filingPeriodQuestionId = "SBA01H"

  val dateLowerBound = new LocalDate(2018, 10, 28)
  val exampleUpperBoundDate = new LocalDate(2019, 10, 28)

  val greaterThanMaxClaimError = Set(CtValidation(Some(s"$claimId.building0"), s"error.$claimId.greaterThanMax" ,None))
}
