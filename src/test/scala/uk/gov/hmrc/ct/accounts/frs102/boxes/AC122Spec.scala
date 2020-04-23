/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{MockAbridgedAccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC122Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
    when(ac43()).thenReturn(AC43(Some(100)))
  }

  "AC122" should {

    "throw error when is different than AC42" in {
      setUpMocks()
      AC122(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.intangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "pass validation if has value but AC42 and AC43 are empty" in {
      import boxRetriever._

      when(ac42()).thenReturn(AC42(None))
      when(ac43()).thenReturn(AC43(None))

      AC122(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation if this and AC43 have values but AC42 is empty" in {
      import boxRetriever._

      when(ac42()).thenReturn(AC42(None))
      when(ac43()).thenReturn(AC43(Some(10)))

      AC122(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.intangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC122(Some(100)).validate(boxRetriever) shouldBe Set.empty
    }

    "correctly perform the calculation when both numbers are set" in {
      import boxRetriever._

      when(ac117()).thenReturn(AC117(Some(1)))
      when(ac121()).thenReturn(AC121(Some(1)))

      AC122.calculate(boxRetriever) shouldBe AC122(Some(0))
    }

    "correctly perform the calculation when only one number is set" in {
      import boxRetriever._

      when(ac117()).thenReturn(AC117(Some(1)))
      when(ac121()).thenReturn(AC121(None))

      AC122.calculate(boxRetriever) shouldBe AC122(Some(1))
    }

    "correctly perform the calculation when no numbers are set" in {
      import boxRetriever._

      when(ac117()).thenReturn(AC117(None))
      when(ac121()).thenReturn(AC121(None))

      AC122.calculate(boxRetriever) shouldBe AC122(None)
    }

  }

}
