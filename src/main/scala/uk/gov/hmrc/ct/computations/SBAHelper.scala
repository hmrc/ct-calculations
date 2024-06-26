/*
 * Copyright 2024 HM Revenue & Customs
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

import java.time.LocalDate

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

  val dateLowerBound = LocalDate.of(2018,10,29)
  val exampleUpperBoundDate = LocalDate.of(2019,10,28)
}
