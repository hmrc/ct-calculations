/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AccountsIntegerValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.box.CtValidation

class AC106Spec extends AccountsIntegerValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever with AdditionalNotesAndFootnotesHelper {

  override def setUpMocks() = {
    super.setUpMocks()

    when(boxRetriever.ac106A()).thenReturn(AC106A(None))
    when(boxRetriever.ac107()).thenReturn(AC107(None))
  }

  override val boxId = "AC106"
  private def validateAC106(inputField: Option[Int], validationResult: Set[CtValidation]) = AC106(inputField).validate(boxRetriever) shouldBe validationResult

  "AC106" should {
    "validate with an error when blank, AC106A blank and AC107 blank" in {
      setUpMocks()
      validateAC106(None, fieldRequiredError(boxId))
    }

    "throw an error when blank, AC106A has a value, AC107 blank" in {
      when(boxRetriever.ac106A()).thenReturn(AC106A(Some("A note")))
      when(boxRetriever.ac107()).thenReturn(AC107(None))
      validateAC106(None, fieldRequiredError(boxId))
    }

    "not validate with any errors when AC106 has an integer value" in {
      validateAC106(Some(10), validationSuccess)
    }

    "validate the input values correctly" when {

      testIntegerFieldValidation(boxId, AC106, Some(minNumberOfEmployees), Some(maxNumberOfEmployees), Some(true))
    }
  }
}
