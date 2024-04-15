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

package object covidSupport {
  //Dates exclusive
  private val eothoStart = LocalDate.parse("2020-08-02")
  private val eothoEnd = LocalDate.parse("2020-09-01")
  private val covidStart = LocalDate.parse("2020-02-29")
  //Covid end TBC

  def doesPeriodCoverCovid(startDate: LocalDate, endDate: LocalDate): Boolean = checkPeriodWithinConstraints(startDate, endDate, Some(covidStart), None)
  def doesPeriodCoverEotho(startDate: LocalDate, endDate: LocalDate): Boolean = checkPeriodWithinConstraints(startDate, endDate, Some(eothoStart), Some(eothoEnd))

  private def checkPeriodWithinConstraints(startDate: LocalDate, endDate: LocalDate, constraintStart: Option[LocalDate], constraintEnd: Option[LocalDate]) = {
    constraintStart.forall(cs => endDate.isAfter(cs)) && constraintEnd.forall(ce => startDate.isBefore(ce))
  }
}
