/*
 * Copyright 2017 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait SummaryCalculator extends CtTypeConverters{

  def calculateTradingLossesBroughtForwardForSummary(cp238: CP238): CP257 = CP257(cp238.value)

  def calculateNetTradingAndProfessionalProfits(cp256: CP256, cp257: CP257): CP258 = CP258(cp256 - cp257.orZero)

  def calculateProfitsAndGainsFromNonTradingLoanRelationships(cp43: CP43): CP259 = CP259(cp43.orZero)

  def calculateTradingLossesOfThisOrLaterAccountingPeriods(cp239: CP239): CP264 = CP264(cp239)

  def calculateQualifyingCharitableDonations(cp301: CP301, cp302: CP302): CP305 = CP305(cp301 + cp302)

  def calculateTradeNetAllowancesForSummary(cp186: CP186, cp668: CP668, cp674: CP674, cp91: CP91, cp670: CP670): CP99 = CP99((cp186 + cp668 + cp674 - cp91 - cp670).max(0))

}
