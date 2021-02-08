/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.frsse2008.{AC16, AC24}
import uk.gov.hmrc.ct.accounts.utils.CovidProfitAndLossSpecHelper
import uk.gov.hmrc.ct.accounts.{AC3, AC4, MockFrsse2008AccountsBoxRetriever}
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany

class AC24Spec extends CovidProfitAndLossSpecHelper with MockFrsse2008AccountsBoxRetriever {

  private val emptyGrossProfitOrLoss: AC16 = AC16(Some(0))
  private def makeAC24(value: Int, box: CtBoxIdentifier with CtOptionalInteger = emptyGrossProfitOrLoss): AC24 = new AC24(Some(value)) {
    override def getCorrectBox(boxRetriever: BoxRetriever): Box = box
  }

  "AC25 validation" should {
    "validate successfully" when {
      "empty" in {
        doMocks()
        boxValidationIsSuccessful(AC24(None).validate(boxRetriever))
      }

      "zero" in {
        doMocks()
        boxValidationIsSuccessful(AC24(Some(0)).validate(boxRetriever))
      }

      "greater than 0 but less than 632000, when combined with AC16" in {
        doMocks()
        val validatedAC24 = makeAC24(5, AC16(Some(500))).validate(boxRetriever)
        boxValidationIsSuccessful(validatedAC24)
      }
    }

    "fail" when {
      "negative" in {
        doMocks()
        val validatedAC24 = makeAC24(-1).validate(boxRetriever)
        doesErrorMessageContain(validatedAC24, zeroOrPositiveErrorMsg)
      }

      "more than 632000" in {
        doMocks()
        val validatedAC24 = makeAC24(justOverLimit).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax("AC16"))
        turnoverTooLargeErrorArguments(validatedAC24)
      }

      "more than 632000 when combined with AC16" in {
        doMocks()
        val grossProfitOrLoss = AC16(Some(2))
        val validatedAC24 = makeAC24(justUnderLimit, grossProfitOrLoss).validate(boxRetriever)

        doesErrorMessageContain(validatedAC24, turnoverBiggerThanMax("AC16"))
        turnoverTooLargeErrorArguments(validatedAC24)
      }
    }
  }

  def doMocks(): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
  }
}
