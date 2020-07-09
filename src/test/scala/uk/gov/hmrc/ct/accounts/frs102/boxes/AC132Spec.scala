/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC132Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  "AC132" should {
    "fail validation when AC132 does not match AC44" in {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(11)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "fail validation when AC132 is set, AC44 is empty and AC45 is set" in {
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(22)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "pass validation when totals tally" in {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(22)).validate(boxRetriever) shouldBe Set()
    }



    "pass validation when no values for note fields or balance sheet value" in {
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(None).validate(boxRetriever) shouldBe Set()
    }
  }
}
