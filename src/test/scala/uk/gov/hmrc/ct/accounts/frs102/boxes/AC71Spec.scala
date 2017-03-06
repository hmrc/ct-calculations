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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.{AC205, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.domain.CompanyTypes

class AC71Spec extends WordSpec with Matchers {

  "AC71 validation if PY is not set" should {
    CompanyTypes.AllCompanyTypes.foreach { companyType =>
      s"return no error for company type '$companyType' if PY is not set and value is empty" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(None))
        AC71(None).validate(boxRetriever) shouldBe Set.empty
      }

      s"return error for company type '$companyType' if PY is not set and value is not empty" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(None))
        AC71(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.cannot.exist"))
      }
    }
  }

  "AC71 validation for NON limited by guarantee" should {
    val companyTypes = CompanyTypes.AllCompanyTypes.filterNot(CompanyTypes.LimitedByGuaranteeCompanyTypes.contains)
    companyTypes.foreach { companyType =>
      s"return error if no value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.required"))
      }

      s"return no errors if value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(2)).validate(boxRetriever) shouldBe empty
      }

      s"be valid when 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(1)).validate(boxRetriever) shouldBe empty
      }

      s"be valid when greater then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(2)).validate(boxRetriever) shouldBe empty
      }

      s"be valid when positive but equals upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(99999999)).validate(boxRetriever) shouldBe empty
      }

      s"fail validation when less then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.below.min", Some(Seq("1", "99999999"))))
      }

      s"fail validation when positive but above upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.above.max", Some(Seq("1", "99999999"))))
      }
    }
  }

  "AC71 validation for limited by guarantee" should {
    CompanyTypes.LimitedByGuaranteeCompanyTypes.foreach { companyType =>
      s"be valid if no value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(None).validate(boxRetriever) shouldBe empty
      }

      s"return cannot exist if value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.cannot.exist"))
      }

      s"be valid when positive but equals upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(99999999)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.cannot.exist"))
      }

      s"fail validation when less then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.cannot.exist"))
      }
    }
  }

}
