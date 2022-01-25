/*
 * Copyright 2022 HM Revenue & Customs
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

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO01}

class CP997Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  private val boxId = Some("CP997")
  private val exceedsNonTradingProfit = "error.CP997.exceeds.nonTradingProfit"

  testMandatoryWhen("CP997", CP997.apply) {
    makeBoxRetriever(cp281bValue = Some(1))
  }

  testBoxIsZeroOrPositive("CP997", CP997.apply)

  "CP997" should {
    "fail validation if it exceeds non trading profit" in {
      CP997(2).validate(makeBoxRetriever()).contains(CtValidation(boxId, exceedsNonTradingProfit)) shouldBe true
    }

    "pass validation if it equals non trading profit" in {
      CP997(2).validate(makeBoxRetriever(cato01Value = 2, cp44Value = 2)).contains(CtValidation(boxId, exceedsNonTradingProfit)) shouldBe false
    }
    "pass validation if it is less than non trading profit" in {
      CP997(1).validate(makeBoxRetriever(cato01Value = 2, cp44Value = 2)).contains(CtValidation(boxId, exceedsNonTradingProfit)) shouldBe false
    }
    "fail validation if the company has trading profit and CP281b - CP283b < CP997" in {
      CP997(2).validate(makeBoxRetriever(tradingProfitOrLoss = 1)).contains(CtValidation(boxId, "error.CP997.exceeds.leftLosses.with.trading.profit")) shouldBe true
    }

    "fail validation if the company has NOT got a trading profit and CP281b < CP997" in {
      CP997(2).validate(makeBoxRetriever()).contains(CtValidation(boxId, "error.CP997.exceeds.leftLosses.without.trading.profit")) shouldBe true
    }
  }

  private def makeBoxRetriever(cp281bValue: Option[Int] = Some(1), cato01Value: Int = 1, cp44Value: Int = 1, cp283bValue: Option[Int] = Some(1), tradingProfitOrLoss: Int = 0) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281b()).thenReturn(CP281b(cp281bValue))
    when(retriever.cp283b()).thenReturn(CP283b(cp283bValue))
    when(retriever.cato01()).thenReturn(CATO01(cato01Value))
    when(retriever.cp44()).thenReturn(CP44(cp44Value))
    when(retriever.cp117()) thenReturn CP117(tradingProfitOrLoss)
    retriever
  }
}
