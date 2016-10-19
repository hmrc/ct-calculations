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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC133(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value of tangible assets at the end of this period")
  with CtOptionalInteger {
}

object AC133 extends Calculated[AC133, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC133 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(x.ac124(), x.ac128())
      case x: FullAccountsBoxRetriever => {
        import x._
        calculateAC133(
          ac133A(),
          ac133B(),
          ac133C(),
          ac133D(),
          ac133E()
        )
      }
    }
  }
}
