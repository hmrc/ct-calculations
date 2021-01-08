/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation
import org.mockito.Mockito._
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.domain.CompanyTypes

class AC70Spec extends WordSpec with Matchers {

  "AC70 validation for NON limited by guarantee" should {
      val companyTypes = CompanyTypes.AllCompanyTypes.filterNot(CompanyTypes.LimitedByGuaranteeCompanyTypes.contains)
      companyTypes.foreach { companyType =>
        s"return error if no value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.required"))
        }

        s"return no errors if value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(2)).validate(boxRetriever) shouldBe empty
        }

        s"be valid when 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(1)).validate(boxRetriever) shouldBe empty
        }

        s"be valid when greater then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(2)).validate(boxRetriever) shouldBe empty
        }

        s"be valid when positive but equals upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(99999999)).validate(boxRetriever) shouldBe empty
        }

        s"fail validation when less then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.below.min", Some(Seq("1", "99999999"))))
        }

        s"fail validation when positive but above upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
          when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
          AC70(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.above.max", Some(Seq("1", "99999999"))))
        }
      }
    }

  "AC70 validation for limited by guarantee" should {
    CompanyTypes.LimitedByGuaranteeCompanyTypes.foreach { companyType =>
      s"be valid if no value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        AC70(None).validate(boxRetriever) shouldBe empty
      }

      s"return cannot exist if value entered for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        AC70(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.cannot.exist"))
      }

      s"be valid when positive but equals upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        AC70(Some(99999999)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.cannot.exist"))
      }

      s"fail validation when less then 1 for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        AC70(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.cannot.exist"))
      }
    }
  }

}
