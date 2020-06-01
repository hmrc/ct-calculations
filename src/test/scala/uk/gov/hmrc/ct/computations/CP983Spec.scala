/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany

class CP983Spec extends WordSpec with Matchers with MockitoSugar {
  "CP983 validation" should {
    "mandatory" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(None)

      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.required"))
    }

    "Can't be negative" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(Some(-1))

      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.mustBeZeroOrPositive"))
    }

    "Can't be more than 632000" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(Some(632001))

      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.above.max", Some(List("-632,000", "632,000"))))
    }

    "Can't be more than 632000 with CP7" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(Some(2))
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(Some(631999))

      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.above.max", Some(List("-632,000", "632,000"))))
    }

    "No errors for value under 632000" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(Some(632000))

      cp983.validate(boxRetriever) shouldBe Set.empty
    }
  }
}
