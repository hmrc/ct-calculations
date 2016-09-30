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

  def calculateTangibleAssetsAtTheEndOFThePeriod(ac124: AC124, ac125: AC125, ac126: AC126, ac212: AC212, ac213: AC213): AC217 = {
    (ac124.value, ac125.value, ac126.value, ac212.value, ac213.value) match {
      case (None, None, None, None, None) => AC217(None)
      case (_) => AC217(Some(ac124.orZero + ac125.orZero - ac126.orZero + ac212.orZero + ac213.orZero))
    }
  }

  def calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(ac128: AC128, ac219: AC219, ac130: AC130, ac214: AC214) = {
    (ac128.value, ac219.value, ac130.value, ac214.value) match {
      case (None, None, None, None) => AC131(None)
      case (_) => AC131 (Some(ac128.orZero + ac219.orZero - ac130.orZero + ac214.orZero))
    }
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(ac124: AC124, ac128: AC128) = {
    (ac124.value, ac128.value) match {
      case (None, None) =>  AC133(None)
      case _ => AC133(Some(ac124.orZero - ac128.orZero))
    }
  }

  def calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(ac217: AC217, ac131: AC131) = {
    (ac217.value, ac131.value) match {
      case (None, None) => AC132(None)
      case _ => AC132(Some(ac217.orZero - ac131.orZero))
    }
  }
}
