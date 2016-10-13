/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{AbridgedFiling, HMRCFiling}

class AC12Spec extends WordSpec with Matchers with MockitoSugar {

  "AC12" should {

    "not do any validation for FRSSE 2008 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2015, 12, 31)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "not do any validation for FRS 102 Hmrc Micro or Joint filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(false))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "not do any validation for FRS 102 Coho Only filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "fail validation for negative value in FRS 102 Coho Only filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))

      AC12(Some(-100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.below.min", Some(Seq("0", "99999999"))))
    }

    "fail as mandatory for FRS 102 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2017, 1, 1)))

      AC12(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.required"))
    }

    "not do any validation if Period of Accounts is 12 months and a FRS 102 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2016, 12, 31)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "pass if has value for FRS 102 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2017, 1, 1)))

      AC12(Some(0)).validate(boxRetriever) shouldBe empty
    }

    "fail if has negative value for FRS 102 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2017, 1, 1)))

      AC12(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.below.min", Some(Seq("0", "99999999"))))
    }

    "fail if has more then 99999999 value for FRS 102 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2017, 1, 1)))

      AC12(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.above.max", Some(Seq("0", "99999999"))))
    }

  }

}

trait TestBoxRetriever extends AccountsBoxRetriever with FilingAttributesBoxValueRetriever
