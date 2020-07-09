/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP281aSpec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP281a", CP281a.apply) {
    makeBoxRetriever()
  }

  testBoxIsZeroOrPositive("CP281a", CP281a.apply)

  testCannotExistWhen("CP281a", CP281a.apply, testDetails = "CPQ17 false") {
    makeBoxRetriever(cpq17Value = false)
  }

  testCannotExistWhen("CP281a", CP281a.apply, testDetails = "before loss reform") {
    makeBoxRetriever(cp2Value = losses.lossReform2017)
  }

  "CP281a" should {
    "fail if the sum of CP283a, CP288a is less than CP281a" in {
      CP281a(3).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281a.breakdown.sum.incorrect")) shouldBe true
    }
    "fail if the sum of CP283a, CP288a is greater than CP281a" in {
      CP281a(1).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281a.breakdown.sum.incorrect")) shouldBe true
    }
    "pass if the sum of CP283a, CP288a is equal to CP281a" in {
      CP281a(2).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281a.breakdown.sum.incorrect")) shouldBe false
    }
  }

  private def makeBoxRetriever(cp2Value: LocalDate = losses.lossReform2017.plusDays(1), cpq17Value: Boolean = true,
                              cp283aValue: Option[Int] = Some(1), cp288aValue: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp2()).thenReturn(CP2(cp2Value))
    when(retriever.cpQ17()).thenReturn(CPQ17(Some(cpq17Value)))
    when(retriever.cp283a()).thenReturn(CP283a(cp283aValue))
    when(retriever.cp288a()).thenReturn(CP288a(cp288aValue))
    retriever
  }

}
