/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, Director, Directors}
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
