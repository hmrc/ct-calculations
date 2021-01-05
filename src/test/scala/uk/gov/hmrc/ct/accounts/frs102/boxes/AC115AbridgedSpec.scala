/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.AccountsMoneyValidationFixture
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.MockAbridgedAccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC115AbridgedSpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
    when(ac43()).thenReturn(AC43(Some(100)))
    when(ac114()).thenReturn(AC114(None))
    when(ac115()).thenReturn(AC115(None))
    when(ac116()).thenReturn(AC116(None))
    when(ac209()).thenReturn(AC209(None))
    when(ac210()).thenReturn(AC210(None))
    when(ac118()).thenReturn(AC118(None))
    when(ac119()).thenReturn(AC119(None))
    when(ac120()).thenReturn(AC120(None))
    when(ac211()).thenReturn(AC211(None))
    when(ac5123()).thenReturn(AC5123(Some("test text")))
  }

  testAccountsMoneyValidationWithMin("AC115", 0, AC115.apply, testEmpty = false)

  "AC115" should {

    "throw global error when none of the fields for the note is entered" in {
      setUpMocks()
      when(boxRetriever.ac5123()).thenReturn(AC5123(None))
      AC115(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.intangibleAssets.atLeastOneEntered"))
    }

    "throw global error when one field was entered but not cannot be set" in {
      setUpMocks()
      when(boxRetriever.ac42()).thenReturn(AC42(None))
      when(boxRetriever.ac43()).thenReturn(AC43(None))
      AC115(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.intangibleAssetsNote.cannot.exist"))
    }

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC115(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
