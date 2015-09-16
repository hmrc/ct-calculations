/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod


trait HmrcAccountingPeriodCalculator {

  def accountingPeriod(params: HmrcAccountingPeriodParameters) : HmrcAccountingPeriodResult = {
    HmrcAccountingPeriodResult(effectiveDate(params.accountingPeriod.cp1.value, params.userStartDate),
                               effectiveDate(params.accountingPeriod.cp2.value, params.userEndDate))
  }

  private def effectiveDate(prepopDate: LocalDate, userDate: Option[LocalDate]) = userDate.getOrElse(prepopDate)
}

case class HmrcAccountingPeriodParameters(accountingPeriod: HmrcAccountingPeriod, userStartDate: Option[LocalDate], userEndDate: Option[LocalDate])

case class HmrcAccountingPeriodResult(startDate: LocalDate, endDate: LocalDate)
