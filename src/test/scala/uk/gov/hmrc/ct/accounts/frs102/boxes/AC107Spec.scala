/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes


import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AC205, AccountsIntegerValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.box.CtValidation

class AC107Spec extends AccountsIntegerValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever with AdditionalNotesAndFootnotesHelper {

  private def validateAC107(inputField: Option[Int], validationResult: Set[CtValidation]) = AC107(inputField).validate(boxRetriever) shouldBe validationResult

  override val boxId: String = "AC107"

  private val previousPeriodOfAccounts:  AC205 = AC205(Some(LocalDate.now()))
  private val emptyPreviousPeriodOfAccounts:  AC205 = AC205(None)

  "AC107" should {

    "have errors if blank and the user has a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
      validateAC107(None, fieldRequiredError(boxId))
    }

    "not validate with any errors when AC107 has a value and user has a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
      validateAC107(Some(10), validationSuccess)
    }

    "not validate with any errors AC107 has no value and the user does not have a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn emptyPreviousPeriodOfAccounts
      validateAC107(None, validationSuccess)
    }

    "validate correctly when the user has a previous accounting period" when {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts

      testIntegerFieldValidation(boxId, AC107, Some(minNumberOfEmployees), Some(maxNumberOfEmployees), Some(false))
    }
  }
}
