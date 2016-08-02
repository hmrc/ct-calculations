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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.TotalShareholdersFundsCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, CtValidation}

case class AC81(value: Option[Int]) extends CtBoxIdentifier(name = "Total Shareholders Funds (previous PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC81", boxRetriever.ac69())
  }
}

object AC81 extends Calculated[AC81, Frs10xAccountsBoxRetriever] with TotalShareholdersFundsCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC81 = {
    import boxRetriever._
    calculatePreviousTotalShareholdersFunds(ac71(), ac77(), ac75())
  }
}
