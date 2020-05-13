/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.AC12

class AP2Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[AccountsBoxRetriever with ComputationsBoxRetriever] {

  trait AccountsBoxRetrieverWithComputationsBoxRetriever extends AccountsBoxRetriever with ComputationsBoxRetriever with FilingAttributesBoxValueRetriever
  override val boxRetriever = mock[AccountsBoxRetrieverWithComputationsBoxRetriever]

  testBoxIsZeroOrPositive("AP2", v => AP2(v))

  override def setUpMocks(): Unit = {
    when(boxRetriever.ap1()).thenReturn(AP1(Some(1)))
    when(boxRetriever.ap2()).thenReturn(AP2(Some(1)))
    when(boxRetriever.ap3()).thenReturn(AP3(Some(1)))
    when(boxRetriever.ac12()).thenReturn(AC12(Some(3)))
  }

  "AP2" should {
    "not allow the total of it, AP1 and AP3 to be greater than AC12" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(2)))
      AP2(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
    "not allow the total of it, AP1 and AP3 to be less than AC12" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(4)))
      AP2(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
    "allow the total of it, AP1 and AP3 to be equal to AC12" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(3)))
      AP2(Some(1)).validate(boxRetriever) shouldBe empty
    }
  }
}
