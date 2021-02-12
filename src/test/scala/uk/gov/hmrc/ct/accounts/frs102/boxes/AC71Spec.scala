/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.{AC205, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.domain.CompanyTypes
import uk.gov.hmrc.ct.utils.CatoLimits._
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
        AC71(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.below.min", Some(Seq("1", oldMaxWithCommas))))
      }

      s"fail validation when positive but above upper limit for companyType: $companyType" in new MockFrs102AccountsRetriever {
        when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
        AC71(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC71"), "error.AC71.above.max", Some(Seq("1", oldMaxWithCommas))))
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
