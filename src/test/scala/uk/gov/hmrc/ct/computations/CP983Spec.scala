/*
 * Copyright 2020 HM Revenue & Customs
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
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.{CATO24, FilingCompanyType}
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
        override def cato24 = CATO24(Some(true))
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(None)

      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.required"))
    }

    "not mandatory if CATO24 is false" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def cato24 = CATO24(Some(false))
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(None)

      cp983.validate(boxRetriever) shouldBe Set.empty
    }

    "Can't be negative" in {
      val boxRetriever = new StubbedComputationsBoxRetriever{
        override def cp1 = CP1(new LocalDate("2019-01-01"))
        override def cp2 = CP2(new LocalDate("2019-12-31"))
        override def cp7 = CP7(None)
        override def cato24 = CATO24(Some(true))
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
        override def cato24 = CATO24(Some(true))
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
        override def cato24 = CATO24(Some(true))
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
        override def cato24 = CATO24(Some(true))
        override def companyType(): FilingCompanyType = FilingCompanyType(UkTradingCompany)
      }
      val cp983 = CP983(Some(632000))

      cp983.validate(boxRetriever) shouldBe Set.empty
    }
  }
}
