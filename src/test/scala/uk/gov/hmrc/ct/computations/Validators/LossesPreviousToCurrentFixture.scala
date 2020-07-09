/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.Validators

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.{CP117, CP283a, CP283b}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait LossesPreviousToCurrentFixture extends WordSpec with Matchers with MockitoSugar {

  def testGlobalErrorsForBroughtForwardGtTotalProfit(box: ComputationsBoxRetriever => ValidatableBox[ComputationsBoxRetriever])(boxRetriever: ComputationsBoxRetriever) = {

    "CP283a + CP283b" should {
      "be allowed to equal total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(50))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(Some("CP283a"), "error.CP283.exceeds.totalProfit")) shouldBe false
      }
      "be allowed to be less than total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(49))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(Some("CP283a"), "error.CP283.exceeds.totalProfit")) shouldBe false
      }
      "no be allowed to be greater than total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(51))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(Some("CP283a"), "error.CP283.exceeds.totalProfit")) shouldBe true
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(Some("CP283b"), "error.CP283.exceeds.totalProfit")) shouldBe true
      }
    }
  }
}
