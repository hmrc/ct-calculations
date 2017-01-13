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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalNetAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, CtValidation}

case class AC69(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (previous PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC69", boxRetriever.ac81(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC69 extends Calculated[AC69, Frs102AccountsBoxRetriever] with TotalNetAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC69 = {
    import boxRetriever._
    calculatePreviousTotalNetAssetsLiabilities(ac63(), ac65(), ac67(), ac151B())
  }
}
