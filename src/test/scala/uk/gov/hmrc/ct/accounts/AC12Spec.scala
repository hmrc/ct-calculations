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

package uk.gov.hmrc.ct.accounts

import org.joda.time.{Days, LocalDate}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{AbridgedFiling, CompaniesHouseFiling, HMRCFiling}
import uk.gov.hmrc.ct.box.ValidatableBox

class AC12Spec extends WordSpec with Matchers with MockitoSugar {



  "AC12" should {

    "not do any validation for FRSSE 2008 Hmrc Abridged filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2015, 12, 31)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2016, 12, 31)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "validate against default range for FRSSE 2008" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2015, 12, 31)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2016, 12, 31)))

      AC12(Int.MaxValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.above.max", Some(List("-99999999", "99999999"))))
      AC12(Int.MinValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.below.min", Some(List("-99999999", "99999999"))))
    }

    "not do any validation for FRS 102 Hmrc Micro or Joint filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(false))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2016, 12, 31)))

      AC12(None).validate(boxRetriever) shouldBe empty
    }

    "not do any validation for FRS 102 Coho Only filing" in {

      val boxRetriever = mock[TestBoxRetriever]

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(true))
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate(2016, 1, 1)))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate(2016, 12, 31)))

      AC12(None).validate(boxRetriever) shouldBe empty
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

    "HMRC only filing" when {
      val isHmrcFiling = true
      val isCoHoFiling = false
      val isAbridgedFiling = false

      def getBoxRetriever(startDate: LocalDate, endDate: LocalDate): TestBoxRetriever = {
        val boxRetriever = mock[TestBoxRetriever]

        when(boxRetriever.ac3()).thenReturn(AC3(startDate))
        when(boxRetriever.ac4()).thenReturn(AC4(endDate))
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(isHmrcFiling))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(isCoHoFiling))
        when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(isAbridgedFiling))

        boxRetriever
      }

      "poa starts before 1st Jan 2016" when {
        val startDate = new LocalDate(2015, 1, 1)
        val endDate = new LocalDate(2015, 12, 31)
        val maximumValue = 632000

        "pass validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is lower than minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }
      }

      "poa starts and ends on the same, non-leap year" when {
        val startDate = new LocalDate(2017, 1, 1)
        val endDate = new LocalDate(2017, 12, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st Match in a leap year, ends on 1st March next year" when {
        val startDate = new LocalDate(2016, 3, 1)
        val endDate = new LocalDate(2017, 3, 1)
        val maximumValue = 633731

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }
        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 28th Feb on non leap year and ends on 28th Feb on a leap year" when {
        val startDate = new LocalDate(2019, 2, 28)
        val endDate = new LocalDate(2020, 2, 28)
        val maximumValue = 633731

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 29th Feb and ends the same year on 31st Dec" when {
        val startDate = new LocalDate(2016, 2, 29)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 530120

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on non-leap year 1st Feb and ends on 29th Feb" when {
        val startDate = new LocalDate(2019, 2, 1)
        val endDate = new LocalDate(2020, 2, 29)
        val maximumValue = 680349

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts 1st Jan on a leap year and ends on 31st Dec" when {
        val startDate = new LocalDate(2016, 1, 1)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st June on a non-leap year and ends on 31st May on a leap year" when {
        val startDate = new LocalDate(2019, 6, 1)
        val endDate = new LocalDate(2020, 5, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

    }

    "Joint filing" when {
      val isHmrcFiling = true
      val isCoHoFiling = true
      val isAbridgedFiling = false

      def getBoxRetriever(startDate: LocalDate, endDate: LocalDate): TestBoxRetriever = {
        val boxRetriever = mock[TestBoxRetriever]

        when(boxRetriever.ac3()).thenReturn(AC3(startDate))
        when(boxRetriever.ac4()).thenReturn(AC4(endDate))
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(isHmrcFiling))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(isCoHoFiling))
        when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(isAbridgedFiling))

        boxRetriever
      }

      "poa starts before 1st Jan 2016" when {
        val startDate = new LocalDate(2015, 1, 1)
        val endDate = new LocalDate(2015, 12, 31)
        val maximumValue = 632000

        "pass validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is lower than minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }
      }

      "poa starts and ends on the same, non-leap year" when {
        val startDate = new LocalDate(2017, 1, 1)
        val endDate = new LocalDate(2017, 12, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation, with hmrc error only, when number is higher than default maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(Int.MaxValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(Int.MaxValue), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation, with hmrc error only, when number is lower than default minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(Int.MinValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(Int.MinValue), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st Match in a leap year, ends on 1st March next year" when {
        val startDate = new LocalDate(2016, 3, 1)
        val endDate = new LocalDate(2017, 3, 1)
        val maximumValue = 633731

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 28th Feb on non leap year and ends on 28th Feb on a leap year" when {
        val startDate = new LocalDate(2019, 2, 28)
        val endDate = new LocalDate(2020, 2, 28)
        val maximumValue = 633731

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 29th Feb and ends the same year on 31st Dec" when {
        val startDate = new LocalDate(2016, 2, 29)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 530120

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on non-leap year 1st Feb and ends on 29th Feb" when {
        val startDate = new LocalDate(2019, 2, 1)
        val endDate = new LocalDate(2020, 2, 29)
        val maximumValue = 680349

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts 1st Jan on a leap year and ends on 31st Dec" when {
        val startDate = new LocalDate(2016, 1, 1)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st June on a non-leap year and ends on 31st May on a leap year" when {
        val startDate = new LocalDate(2019, 6, 1)
        val endDate = new LocalDate(2020, 5, 31)
        val maximumValue = 632000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.hmrc.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

    }

    "CoHo only filing" when {
      val isHmrcFiling = false
      val isCoHoFiling = true
      val isAbridgedFiling = false

      def getBoxRetriever(startDate: LocalDate, endDate: LocalDate): TestBoxRetriever = {
        val boxRetriever = mock[TestBoxRetriever]

        when(boxRetriever.ac3()).thenReturn(AC3(startDate))
        when(boxRetriever.ac4()).thenReturn(AC4(endDate))
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(isHmrcFiling))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(isCoHoFiling))
        when(boxRetriever.abridgedFiling()).thenReturn(AbridgedFiling(isAbridgedFiling))

        boxRetriever
      }

      "poa starts before 1st Jan 2016" when {
        val startDate = new LocalDate(2015, 1, 1)
        val endDate = new LocalDate(2015, 12, 31)
        val maximumValue = 632000

        "pass validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is lower than minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }
      }

      "poa starts and ends on the same, non-leap year" when {
        val startDate = new LocalDate(2017, 1, 1)
        val endDate = new LocalDate(2017, 12, 31)
        val maximumValue = 10200000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation, with coho error only, when number is higher than default maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(Int.MaxValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(Int.MaxValue), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation, with coho error only, when number is lower than default minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(Int.MinValue).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(Int.MinValue), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st Match in a leap year, ends on 1st March next year" when {
        val startDate = new LocalDate(2016, 3, 1)
        val endDate = new LocalDate(2017, 3, 1)
        val maximumValue = 10227945

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 28th Feb on non leap year and ends on 28th Feb on a leap year" when {
        val startDate = new LocalDate(2019, 2, 28)
        val endDate = new LocalDate(2020, 2, 28)
        val maximumValue = 10227945

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 29th Feb and ends the same year on 31st Dec" when {
        val startDate = new LocalDate(2016, 2, 29)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 8555737

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on non-leap year 1st Feb and ends on 29th Feb" when {
        val startDate = new LocalDate(2019, 2, 1)
        val endDate = new LocalDate(2020, 2, 29)
        val maximumValue = 10980327

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts 1st Jan on a leap year and ends on 31st Dec" when {
        val startDate = new LocalDate(2016, 1, 1)
        val endDate = new LocalDate(2016, 12, 31)
        val maximumValue = 10200000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

      "poa starts on 1st June on a non-leap year and ends on 31st May on a leap year" when {
        val startDate = new LocalDate(2019, 6, 1)
        val endDate = new LocalDate(2020, 5, 31)
        val maximumValue = 10200000

        "fail validation when number is higher than maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue + 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.above.max", Some(Seq(ValidatableBox.commaForThousands(maximumValue + 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "fail validation when number is lower than minimum value (min threshold is positive to simplify rendering)" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue - 1).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC12"), "error.AC12.coho.turnover.below.min", Some(Seq(ValidatableBox.commaForThousands(-maximumValue - 1), ValidatableBox.commaForThousands(maximumValue)))))
        }

        "pass validation when number is exactly on maximum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

        "pass validation when number is exactly on minimum value" in {
          val boxRetriever = getBoxRetriever(startDate, endDate)
          AC12(-maximumValue).validate(boxRetriever) shouldBe Set.empty
        }

      }

    }

  }

}

trait TestBoxRetriever extends AccountsBoxRetriever with FilingAttributesBoxValueRetriever
