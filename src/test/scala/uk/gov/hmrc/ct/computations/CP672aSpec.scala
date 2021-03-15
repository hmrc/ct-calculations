/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Assertion, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.UnitSpec


class CP672aSpec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with UnitSpec{

  val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

 private def hasCompanyCeasedTradingMock(hasCompanyCeasedTrading: Boolean) = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(hasCompanyCeasedTrading)))
  }

  private val lowerThan672a: Option[Int] = Some(50)

  override def setUpMocks() = {
    when(boxRetriever.cp672()) thenReturn CP672(lowerThan672a)
    hasCompanyCeasedTradingMock(false)
  }


  testBoxIsZeroOrPositive("CP672a", CP672a.apply)

  "CP672a should fail validation" when {
    val cp672aId = "CP672a"

    def exceeds672aError(boxBeingComparedWith: String): Assertion =
      CP672a(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP672a"), s"error.$cp672aId.$boxBeingComparedWith.exceeds.max", Some(List("50"))))

    "when greater than CP84 and the company has ceased trading (CPQ8 = true)" in {
      hasCompanyCeasedTradingMock(true)
      when(boxRetriever.cp84()) thenReturn CP84(lowerThan672a)

      exceeds672aError("CP84")
    }

    " when greater than CP672 and the company has not ceased trading (CPQ8 = false)" in {
      hasCompanyCeasedTradingMock(false)
      when(boxRetriever.cp672()) thenReturn CP672(lowerThan672a)

      exceeds672aError("CP672")
    }
  }
    "CP672a should pass validation" when  {
     "less than CP84 and the company has ceased trading (CPQ8 = true)" in {
       hasCompanyCeasedTradingMock(true)
       when(boxRetriever.cp84()) thenReturn CP84(50)

      CP672a(Some(40)).validate(boxRetriever) shouldBe validationSuccess
    }

      "less than CP672 and the company has ceased trading (CPQ8 = false)" in {
       hasCompanyCeasedTradingMock(false)
       when(boxRetriever.cp672()) thenReturn CP672(50)

      CP672a(Some(40)).validate(boxRetriever) shouldBe validationSuccess
    }

  }
}
