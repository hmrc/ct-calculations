/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.{CompaniesHouseFiling, FilingCompanyType, HMRCFiling}
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoLimits._
import uk.gov.hmrc.ct.utils.UnitSpec

class AC16Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with UnitSpec with MockFrs102AccountsRetriever {

  private def doMocks(hmrcFiling: Boolean): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
    if (hmrcFiling) {
      when(boxRetriever.hmrcFiling()) thenReturn HMRCFiling(true)
      when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(false)
    } else {
      when(boxRetriever.hmrcFiling()) thenReturn HMRCFiling(false)
      when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(true)
    }
  }

  private val ac16 = "AC16"


  "AC16" should {
    "fail validation" when {
      "its value is greater than the apportioned max turnover and the user is going through an hmrc journey" in {
        doMocks(true)

        AC16(Some(turnoverHMRCMaxValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(
          Some(ac16),
          s"error.$ac16.hmrc.turnover.above.max",
          Some(List("0", turnoverHMRCMaxWithCommas))))
      }

      "its value is greater than the apportioned max turnover user is going through an coho journey" in {
        doMocks(false)

        AC16(Some(turnoverCOHOMaxValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(
          Some(ac16),
          s"error.$ac16.coho.turnover.above.max",
          Some(List("-" + turnoverCOHOMaxWithCommas, turnoverCOHOMaxWithCommas))))
      }

      "empty" in {
        doMocks(true)
        AC16(None).validate(boxRetriever) shouldBe fieldRequiredError(ac16)
      }
    }
    "pass validation" when {
      "its value is less than the apportioned max turnover and the user is going through an hmrc journey" in {
        doMocks(true)
        AC16(Some(turnoverHMRCMaxValue)).validate(boxRetriever) shouldBe validationSuccess
      }
      "its value is less than the apportioned max turnover user is going through an coho journey" in {
        doMocks(false)
        AC16(Some(turnoverCOHOMaxValue)).validate(boxRetriever) shouldBe validationSuccess
      }

    }
  }
}
