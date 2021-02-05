/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.{AC8021, Director, Directors}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC304ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever with Frs10xDirectorsBoxRetriever]
  with BeforeAndAfterEach {

  override protected def beforeEach(): Unit = {
    when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
    when(boxRetriever.directors()).thenReturn(Directors(List(Director("1", "Test dude one"), Director("2", "Test dude two"))))
  }

  testTextFieldValidation("AC304A", AC304A, testLowerLimit = Some(1), testUpperLimit = Some(StandardCohoNameFieldLimit), testMandatory = Some(true))

  "AC304A should" should {

    "fail validation when not set" in {
      AC304A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC304A"), "error.AC304A.required", None))
    }
    "fail validation when not legal name" in {
      AC304A(Some("bad n&me£")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC304A"), "error.AC304A.regexFailure"))
    }

    "pass validation when using custom director name and directors' report is not attached" in {
      AC304A(Some("custom name")).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation when using custom director name and directors' report is attached" in {
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      AC304A(Some("custom name")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC304A"), "error.loansToDirectors.invalidDirectorName"))
    }

    "pass validation when using existing director and directors' report is attached" in {
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      AC304A(Some("Test dude one")).validate(boxRetriever) shouldBe Set.empty
      AC304A(Some("Test dude two")).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
