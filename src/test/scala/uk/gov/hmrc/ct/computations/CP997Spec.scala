/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO01}

class CP997Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP997", CP997.apply) {
    makeBoxRetriever(cp281bValue = Some(1))
  }

  testBoxIsZeroOrPositive("CP997", CP997.apply)

  "CP997" should {
    "fail validation if it exceeds non trading profit" in {
      CP997(2).validate(makeBoxRetriever()).contains(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit")) shouldBe true
    }
    "fail validation if cp281b and cp283b don't sum up" in {
      CP997(2).validate(makeBoxRetriever()).contains(CtValidation(Some("CP997"), "error.CP997.exceeds.leftLosses")) shouldBe true
    }
    "pass validation if it equals non trading profit" in {
      CP997(2).validate(makeBoxRetriever(cato01Value = 2, cp44Value = 2)).contains(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit")) shouldBe false
    }
    "pass validation if it is less than non trading profit" in {
      CP997(1).validate(makeBoxRetriever(cato01Value = 2, cp44Value = 2)).contains(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit")) shouldBe false
    }
  }

  private def makeBoxRetriever(cp281bValue: Option[Int] = Some(1), cato01Value: Int = 1, cp44Value: Int = 1, cp283bValue: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281b()).thenReturn(CP281b(cp281bValue))
    when(retriever.cp283b()).thenReturn(CP283b(cp283bValue))
    when(retriever.cato01()).thenReturn(CATO01(cato01Value))
    when(retriever.cp44()).thenReturn(CP44(cp44Value))
    retriever
  }
}
