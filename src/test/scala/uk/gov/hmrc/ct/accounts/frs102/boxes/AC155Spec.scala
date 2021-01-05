/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC155Spec extends WordSpec with Matchers with MockitoSugar {

  "AC155" should {

    "have no errors if AC59 has the same value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC155(Some(50)).validate(boxRetriever) shouldBe empty
    }

    "have no errors if AC59 and AC155 are both None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      AC155(None).validate(boxRetriever) shouldBe empty
    }

    "have no errors if AC59 and AC155 are both 0" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(0)))
      AC155(Some(0)).validate(boxRetriever) shouldBe empty
    }

    "return error if AC59 is 0 and AC155 is None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(0)))
      AC155(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.previous.total.not.equal.balance.sheet"))
    }

    "return error if AC59 is 0 and AC155 is 50" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(0)))
      AC155(Some(50)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.previous.total.not.equal.balance.sheet"))
    }

  }

}
