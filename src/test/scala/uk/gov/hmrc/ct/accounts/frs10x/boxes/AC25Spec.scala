/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.frs10x.{AC13, AC25}
import uk.gov.hmrc.ct.accounts.{AC3, AC4, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany

class AC25Spec extends WordSpec with Matchers with MockitoSugar with MockFullAccountsRetriever {
  "AC25 validation" should {
    "empty" in {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
      when(boxRetriever.ac13()).thenReturn(AC13(Some(0)))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      AC25(None).validate(boxRetriever) shouldBe empty

    }

    "Can't be negative" in {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-01-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2019-12-31")))
      when(boxRetriever.ac13()).thenReturn(AC13(Some(0)))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      val ac25 = AC25(Some(-1))

      ac25.validate(boxRetriever) shouldBe Set(CtValidation(Some("AC25"), "error.AC25.hmrc.turnover.below.min",Some(List("0", "632,000"))))
    }

    "Can't be more than 632000" in {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
      when(boxRetriever.ac13()).thenReturn(AC13(Some(0)))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      val ac25 = AC25(Some(6320001))

      ac25.validate(boxRetriever) shouldBe Set(CtValidation(Some("AC25"), "error.AC25.hmrc.turnover.above.max",Some(List("0", "632,000"))))
    }


    "Can't be more than 632000 with AC13" in {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
      when(boxRetriever.ac13()).thenReturn(AC13(Some(2)))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      val ac25 = AC25(Some(631999))

      ac25.validate(boxRetriever) shouldBe Set(CtValidation(Some("AC25"), "error.AC25.hmrc.turnover.above.max",Some(List("0", "632,000"))))
    }


    "No errors for value under 632000" in {
      when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
      when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
      when(boxRetriever.ac13()).thenReturn(AC13(Some(0)))
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
      val ac25 = AC25(Some(632000))

        ac25.validate(boxRetriever) shouldBe Set.empty
      }
    }
}
