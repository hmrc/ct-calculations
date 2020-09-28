/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.{AbridgedFiling, MicroEntityFiling, StatutoryAccountsFiling}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC8084Spec extends WordSpec
                 with MockitoSugar
                 with Matchers
                 with BeforeAndAfter
                 with MockFrs102AccountsRetriever {

  "AC8084" should {

    "pass validation if is empty and filing full accounts" in {
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(false))
      when(boxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

      AC8084(None).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation if is empty and filing other type" in {
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(false))
      when(boxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

      AC8084(None).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation if is empty and filing abridged accounts" in {
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

      AC8084(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC8084"), "error.AC8084.required"))
    }

    "pass validation if not empty and filing abridged accounts" in {
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

      AC8084(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
