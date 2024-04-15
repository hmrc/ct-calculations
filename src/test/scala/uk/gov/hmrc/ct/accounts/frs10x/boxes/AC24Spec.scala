/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import java.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.{CompaniesHouseFiling, FilingCompanyType}
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4, MockFrs10xAccountsRetriever}
import uk.gov.hmrc.ct.accounts.utils.CovidProfitAndLossSpecHelper
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoInputBounds._

class AC24Spec extends CovidProfitAndLossSpecHelper with MockFrs10xAccountsRetriever {

  private val validGrossProfitAndLoss: AC16 = AC16(Some(12))
  private val emptyTurnover: AC12 = AC12(Some(0))
  private val validTurnover: AC12 = AC12(Some(50))

  private def makeAC24(value: Int, ac16OrAc12Box: CtBoxIdentifier with CtOptionalInteger): AC24 = new AC24(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = ac16OrAc12Box
  }

  "AC24 validation" should {
    "validate successfully in the coho journey" when {
      s"greater than 0 but less than $turnoverCOHOMaxWithCommas, when combined with AC12" in new Setup {
        doMocks(true)
        boxValidationIsSuccessful(makeAC24(5, validTurnover).validate(boxRetriever))
      }

      s"greater than 0 but less than $turnoverCOHOMaxWithCommas, when combined with AC16" in new Setup {
        doMocks(true)
        boxValidationIsSuccessful(makeAC24(5, validGrossProfitAndLoss).validate(boxRetriever))
      }
    }
    "validate successfully in the hmrc journey" when {
      s"greater than 0 but less than $turnoverHMRCMaxValue632k, when combined with AC12" in new Setup {
        doMocks(false)
        boxValidationIsSuccessful(makeAC24(5, emptyTurnover).validate(boxRetriever))
      }

      s"greater than 0 but less than $turnoverHMRCMaxValue632k, when combined with AC16" in new Setup {
        doMocks(false)
        boxValidationIsSuccessful(makeAC24(5, validGrossProfitAndLoss).validate(boxRetriever))
      }
    }
    "validate successfully" when {
      "empty" in {
        boxValidationIsSuccessful(AC24(None).validate(boxRetriever))
      }
      "zero" in {
        boxValidationIsSuccessful(AC24(Some(0)).validate(boxRetriever))
      }
    }

    "fail in a non-coho journey" when {
      s"more than $turnoverHMRCMaxValue632k" in new Setup {
        doMocks(false)
        val validatedAC24: Set[CtValidation] = makeAC24(justOverLimit(turnoverHMRCMaxValue632k), emptyTurnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24)
      }


      s"more than $turnoverHMRCMaxValue632k when combined with AC12" in new Setup {
        doMocks(false)
        val turnover = AC12(Some(2))
        val validatedAC24 = makeAC24(justUnderLimit(turnoverHMRCMaxValue632k), turnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24)
      }

      s"more than $turnoverHMRCMaxValue632k when combined with AC16" in new Setup {
        doMocks(false)
        val validatedAC24 = makeAC24(justUnderLimit, validGrossProfitAndLoss).validate(boxRetriever)
        val expectedArguments: Option[List[String]] = Some(List(oldMinWithCommas, turnoverHMRCMaxWithCommas))

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac16Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24, expectedArguments)
      }
    }
    "fail in a coho journey" when {
      s"more than $turnoverCOHOMaxWithCommas" in new Setup {
        doMocks(true)
        val validatedAC24 = makeAC24(justOverLimit(turnoverCOHOMaxValue10m), emptyTurnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }


      s"more than $turnoverCOHOMaxWithCommas when combined with AC12" in new Setup {
        doMocks(true)
        val turnover = AC12(Some(2))
        val validatedAC24 = makeAC24(justUnderLimit(turnoverCOHOMaxValue10m), turnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }

      s"more than $turnoverCOHOMaxWithCommas when combined with AC16" in new Setup {
        doMocks(true)
        val validatedAC24 = makeAC24(justUnderLimit(turnoverCOHOMaxValue10m), validGrossProfitAndLoss).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac16Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }
    }

    "validate a joint journey as expected" when {
      s"gross profit or loss is over $turnoverHMRCMaxValue632k then it should fail validation" in new Setup {
        doMocks(true)
        when(boxRetriever.isJointFiling()) thenReturn true
        val validatedAC24: Set[CtValidation] = makeAC24(justOverLimit(turnoverHMRCMaxValue632k), validGrossProfitAndLoss).validate(boxRetriever)
        val expectedArguments: Option[List[String]] = Some(List(oldMinWithCommas, turnoverHMRCMaxWithCommas))

        turnoverTooLargeErrorArguments(validatedAC24, expectedArguments)
        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac16Id, isCohoJourney = false))
      }

      s"gross profit or loss is less than $turnoverHMRCMaxValue632k, then it should pass validation" in new Setup {
        doMocks(true)
        when(boxRetriever.isJointFiling()) thenReturn true

        val validatedAC24: Set[CtValidation] =
          makeAC24(justUnderLimit(turnoverHMRCMaxValue632k), AC16(Some(0))).validate(boxRetriever)

         validatedAC24 shouldBe validationSuccess
      }
    }

    "fail when negative" in {
      val validatedAC24 = makeAC24(-1, emptyTurnover).validate(boxRetriever)
      doesErrorMessageContain(validatedAC24, zeroOrPositiveErrorMsg)
    }
  }

  trait Setup {
    def doMocks(isCohoJourney: Boolean): Unit = {
      when(boxRetriever.ac3()).thenReturn(AC3(LocalDate.parse("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(LocalDate.parse("2020-08-31")))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      if (isCohoJourney) {
        when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(true)
      } else {
        when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(false)
      }
    }
  }

}
