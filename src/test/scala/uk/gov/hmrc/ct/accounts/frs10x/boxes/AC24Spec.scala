/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.{CompaniesHouseFiling, FilingCompanyType}
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4, MockFrs10xAccountsRetriever}
import uk.gov.hmrc.ct.accounts.utils.CovidProfitAndLossSpecHelper
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoLimits._

class AC24Spec extends CovidProfitAndLossSpecHelper with MockFrs10xAccountsRetriever {

  private def makeAC24(value: Int, box: CtBoxIdentifier with CtOptionalInteger): AC24 = new AC24(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = box
  }

  private val validGrossProfitAndLoss: AC16 = AC16(Some(12)) 
  private val emptyTurnover: AC12 = AC12(Some(0))
  private val validTurnover: AC12 = AC12(Some(50))

  "AC24 validation" should {
    "validate successfully in the coho journey" when {
      s"greater than 0 but less than $turnoverCOHOMaxWithCommas, when combined with AC12" in new Setup{
        doMocks(true)
        boxValidationIsSuccessful(makeAC24(5, validTurnover).validate(boxRetriever))
      }

      s"greater than 0 but less than $turnoverCOHOMaxWithCommas, when combined with AC16" in new Setup {
        doMocks(true)
        boxValidationIsSuccessful(makeAC24(5, validGrossProfitAndLoss).validate(boxRetriever))
      }
    }
    "validate successfully in the hmrc journey" when {
        s"greater than 0 but less than $turnoverHMRCMaxValue, when combined with AC12" in new Setup {
        doMocks(false)
        boxValidationIsSuccessful(makeAC24(5, emptyTurnover).validate(boxRetriever))
      }

        s"greater than 0 but less than $turnoverHMRCMaxValue, when combined with AC16" in new Setup{
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
        s"more than $turnoverHMRCMaxValue" in new Setup {
        doMocks(false)
        val validatedAC24 = makeAC24(justOverLimit(turnoverHMRCMaxValue), emptyTurnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24)
      }


        s"more than $turnoverHMRCMaxValue when combined with AC12" in new Setup {
        doMocks(false)
        val turnover = AC12(Some(2))
        val validatedAC24 = makeAC24(justUnderLimit(turnoverHMRCMaxValue), turnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24)
      }

        s"more than $turnoverHMRCMaxValue when combined with AC16" in new Setup {
        doMocks(false)
        val validatedAC24 = makeAC24(justUnderLimit, validGrossProfitAndLoss).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac16Id, isCohoJourney = false))
        turnoverTooLargeErrorArguments(validatedAC24)
      }
    }
    "fail in a coho journey" when {
      s"more than $turnoverCOHOMaxWithCommas" in new Setup {
        doMocks(true)
        val validatedAC24 = makeAC24(justOverLimit(turnoverCOHOMaxValue), emptyTurnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }


        s"more than $turnoverCOHOMaxWithCommas when combined with AC12" in new Setup {
        doMocks(true)
        val turnover = AC12(Some(2))
        val validatedAC24 = makeAC24(justUnderLimit(turnoverCOHOMaxValue), turnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac12Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }

        s"more than $turnoverCOHOMaxWithCommas when combined with AC16" in new Setup {
        doMocks(true)
        val validatedAC24 = makeAC24(justUnderLimit(turnoverCOHOMaxValue), validGrossProfitAndLoss).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax(ac16Id, isCohoJourney = true))
        turnoverTooLargeErrorArguments(validatedAC24, Some(List("-10,200,000", "10,200,000")))
      }
    }

    "fail when negative" in {
      val validatedAC24 = makeAC24(-1, emptyTurnover).validate(boxRetriever)
      doesErrorMessageContain(validatedAC24, zeroOrPositiveErrorMsg)
    }
  }

  trait Setup {
     def doMocks(isCohoJourney: Boolean): Unit = {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      if (isCohoJourney) {
        when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(true)
      } else {
        when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(false)
      }
    }
  }
}
