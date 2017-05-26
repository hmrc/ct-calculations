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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP3020Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  "CP3020" when {
    "the AP ends before 1/4/17" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2016-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-03-31")))
      "not allow a positive value" in {
        CP3020(1).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£0"))))
      }
      "allow a 0 value" in {
        CP3020(0).validate(retriever) shouldBe empty
      }
    }
    "the AP ends a long time before 1/4/17" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2016-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-03-01")))
      "not allow a positive value" in {
        CP3020(1).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£0"))))
      }
      "allow a 0 value" in {
        CP3020(0).validate(retriever) shouldBe empty
      }
    }
    "the AP starts before 1/4/17 and it ends on 1/4/17" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2016-12-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-04-01")))
      "allow 1 day's worth of grassroots" in {
        CP3020(6).validate(retriever) shouldBe empty
      }
      "not allow more thatn 1 day's worth of grassroots" in {
        CP3020(7).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£6"))))
      }
    }
    "the AP starts before 1/4/17 and it ends after 1/4/17" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2016-12-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-04-30")))
      "allow the pro rata amount of £2500 grassroots" in {
        CP3020(205).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(206).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£205"))))
      }
    }
    "the AP starts on 1/4/17 and ends before 31/3/18" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2017-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-10-31")))
      "allow the pro rata amount of £2500 grassroots" in {
        CP3020(1465).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(1466).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£1465"))))
      }
    }
    "the AP starts on 1/4/17 and ends on 31/3/18" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2017-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2018-03-31")))
      "allow the full amount of £2500 grassroots" in {
        CP3020(2500).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(2501).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£2500"))))
      }
    }
    "the AP starts after 1/4/17 and is less than a year long" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2018-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2018-10-31")))
      "allow the pro rata amount of £2500 grassroots" in {
        CP3020(1465).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(1466).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£1465"))))
      }
    }
    "the AP starts after 1/4/17 and ends a year later" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2018-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-03-31")))
      "allow the full amount of £2500 grassroots" in {
        CP3020(2500).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(2501).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£2500"))))
      }
    }
    "the AP ends in a leap year and doesn't contain 29/2" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-02-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2020-01-31")))
      "allow the full amount of £2500 grassroots" in {
        CP3020(2500).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(2501).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£2500"))))
      }
    }
    "the AP ends in a leap year and contains 29/2" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-04-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2020-03-31")))
      "allow the full amount of £2500 grassroots" in {
        CP3020(2500).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(2501).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£2500"))))
      }
    }
    "the AP starts in a leap year and contains 29/2" should {
      val retriever = makeBoxRetriever(true)
      when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2020-02-01")))
      when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2021-01-31")))
      "allow the full amount of £2500 grassroots" in {
        CP3020(2500).validate(retriever) shouldBe empty
      }
      "not allow more than the pro rata amount of £2500 grassroots" in {
        CP3020(2501).validate(retriever) shouldBe Set(CtValidation(Some("CP3020"), "error.CP3020.apportionedLimit.exceeded", Some(Seq("£2500"))))
      }
    }
  }

  testMandatoryWhen("CP3020", CP3020.apply) {
    makeBoxRetriever(cpq321Value = true)
  }

  testBoxIsZeroOrPositive("CP3020", CP3020.apply)

  testCannotExistWhen("CP3020", CP3020.apply) {
    makeBoxRetriever(cpq321Value = false)
  }

  private def makeBoxRetriever(cpq321Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
    when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
    when(retriever.cpQ321()).thenReturn(CPQ321(Some(cpq321Value)))
    retriever
  }
}
