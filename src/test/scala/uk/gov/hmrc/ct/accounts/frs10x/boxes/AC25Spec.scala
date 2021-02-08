/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.utils.CovidProfitAndLossSpecHelper
import uk.gov.hmrc.ct.accounts.{AC3, AC4, MockFrs10xAccountsRetriever}
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany

class AC25Spec extends CovidProfitAndLossSpecHelper with MockFrs10xAccountsRetriever {

  private def makeAC25(value: Int, box: CtBoxIdentifier with CtOptionalInteger = emptyTurnoverPreviousPeriod): AC25 = new AC25(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = box
  }

  "AC25 validation" should {
    "validate successfully" when {
      "empty" in {
        doMocks()
        boxValidationIsSuccessful(AC25(None).validate(boxRetriever))
      }
      "zero" in {
        doMocks()
        boxValidationIsSuccessful(AC25(Some(0)).validate(boxRetriever))
      }
      "greater than 0 but less than 632000, when combined with AC13" in {
        doMocks()
        boxValidationIsSuccessful(makeAC25(5).validate(boxRetriever))
      }

      "greater than 0 but less than 632000, when combined with AC17" in {
        doMocks()
        boxValidationIsSuccessful(makeAC25(5, AC17(Some(12))).validate(boxRetriever))
      }
    }
    "fail" when {
      "when negative" in {
        doMocks()
        val validatedAC25 = makeAC25(-1).validate(boxRetriever)
        doesErrorMessageContain(validatedAC25, zeroOrPositiveErrorMsg)
      }

      "more than 632000" in {
        doMocks()
        val validatedAC25 = makeAC25(justOverLimit).validate(boxRetriever)

        doesErrorMessageContain(validatedAC25, turnoverBiggerThanMax("AC13"))
        turnoverTooLargeErrorArguments(validatedAC25)
      }


      "more than 632000 when combined with AC13" in {
        doMocks()
        val turnover = AC13(Some(2))
        val validatedAC25 = makeAC25(justUnderLimit, turnover).validate(boxRetriever)

        doesErrorMessageContain(validatedAC25, turnoverBiggerThanMax("AC13"))
        turnoverTooLargeErrorArguments(validatedAC25)
      }

      "more than 632000 when combined with AC17" in {
        doMocks()
        val grossProfitOrLoss = AC17(Some(2))
        val validatedAC25 = makeAC25(justUnderLimit, grossProfitOrLoss).validate(boxRetriever)

        doesErrorMessageContain(validatedAC25, turnoverBiggerThanMax("AC17"))
        turnoverTooLargeErrorArguments(validatedAC25)
      }
    }
  }

  private def doMocks(): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.ac13()).thenReturn(AC13(Some(0)))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
  }
}
