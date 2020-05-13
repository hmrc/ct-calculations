/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC205}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7210BSpec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] {

  when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
  when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(123)))
  testAccountsMoneyValidationWithMin("AC7210B", minValue = 0, AC7210B)

  "AC7210B" should {
    "when AC7210A is empty" when {
      "pass validation if AC7210B has a value AC7200 is true" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210B is empty and AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210B is empty and AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation if AC7210B has a value AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
      "fail validation if AC7210B has a value AC7200 is true and NO previous PoA" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        when(boxRetriever.ac205()).thenReturn(AC205(None))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
      "fail validation if AC7210B has a value AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(None))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
    }

    "when AC7210A is populated" when {
      "pass validation if AC7210B has a value AC7200 is true" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210B is empty and AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210B is empty and AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation if AC7210B has a value AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
      "fail validation if AC7210B has a value AC7200 is true and NO PoA" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(None))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
      "fail validation if AC7210B has a value AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210A()).thenReturn(AC7210A(Some(4321)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210B(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210B"), "error.AC7210B.cannot.exist"))
      }
    }
  }
}
