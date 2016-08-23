/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait BalanceSheetTangibleAssetsCalculator extends CtTypeConverters {

  def calculateTangibleAssetsAtTheEndOFThePeriod(ac5217: AC5217, ac125: AC125, ac126: AC126, ac212: AC212, ac213: AC213): AC217 = {
    AC217(ac5217.orZero + ac125.orZero - ac126.orZero + ac212.orZero + ac213.orZero)
  }

  def calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(ac5131: AC5131, ac219: AC219, ac130: AC130, ac214: AC214) = {
    AC131(ac5131.orZero + ac219.orZero - ac130.orZero + ac214.orZero)
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(ac5217: AC5217, ac5131: AC5131) = {
    AC5132(ac5217.orZero - ac5131.orZero)
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(ac217: AC217, ac131: AC131) = {
    AC132(ac217 - ac131)
  }
}
