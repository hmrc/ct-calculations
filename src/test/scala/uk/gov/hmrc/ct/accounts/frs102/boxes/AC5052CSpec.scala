/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC205}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5052CSpec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with BeforeAndAfter {

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac53).thenReturn(AC53(Some(STANDARD_MAX + 1)))
  }

  testAccountsMoneyValidationWithMin("AC5052C", minValue = 0, AC5052C)

  "pass the validation if AC52 and AC205 are set" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac205()).thenReturn(AC205(Some(LocalDate.parse("2016-01-01"))))
    AC5052C(Some(4)).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when greater than AC53" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(35)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052C"), "error.AC5052C.mustBeLessOrEqual.AC53"))
  }

  "pass validation when equals AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(30)).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when less than AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(25)).validate(boxRetriever) shouldBe Set.empty
  }
 }
