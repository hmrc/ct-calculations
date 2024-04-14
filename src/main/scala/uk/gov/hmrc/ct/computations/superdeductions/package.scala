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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.{AC5, AC6}

package object superdeductions {
  private val superDeductionStart = AC5(new LocalDate("2021-04-01"))
  private val superDeductionEnd = AC6(new LocalDate("2023-03-31"))
  private val superDPeriod = SuperDeductionPeriod(superDeductionStart, superDeductionEnd)

  def isThereSuperDeductionOverLap(cp1: CP1, cp2: CP2):Boolean = {
    HmrcAccountingPeriod(cp1, cp2).overlapsSuperDeduction(superDPeriod)
  }

  def superDeductionsPercentage(cp1: CP1, cp2: CP2):BigDecimal = {
    SuperDeductionPercentage(HmrcAccountingPeriod(cp1, cp2), superDPeriod).percentage
  }
}
