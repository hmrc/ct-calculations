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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.TotalNetAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._

case class AC69(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (previous PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs105AccountsBoxRetriever]
  with AssetsEqualToSharesValidator {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {

    validateAssetsEqualToShares("AC69", boxRetriever.ac491())
  }
}

object AC69 extends Calculated[AC69, Frs105AccountsBoxRetriever] with TotalNetAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC69 = {
    import boxRetriever._
    calculatePreviousTotalNetAssetsLiabilities(ac63(), ac65(), ac67(), ac471())
  }
}
