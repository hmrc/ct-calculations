/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoLimits._

class AC16Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with MockFrs102AccountsRetriever {


  private def doMocks(): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
  }

  doMocks()

  "AC16" should {
    "fail validation" when {
      "its value is greater than the apportioned max turnover" in { //Some("AC16.hmrc.turnover.above.max")
        AC16(Some(turnoverHMRCMaximumValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(
          Some("AC16"),
          "error.AC16.hmrc.turnover.above.max",
          Some(List("0", turnoverHMRCMaximumWithCommas))))
      }
    }
  }
  }
