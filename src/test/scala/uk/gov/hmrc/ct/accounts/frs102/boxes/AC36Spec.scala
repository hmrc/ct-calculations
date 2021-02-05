/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC403}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.frs10x.{AC16, ACQ8161, ACQ8999}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.accounts.frsAny.boxes.AC14
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.UnitSpec
import uk.gov.hmrc.ct.{CATO24, CompaniesHouseFiling, HMRCFiling}

sealed trait AbridgedBoxRetrieverForTest extends AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever with Frs10xDormancyBoxRetriever
sealed trait FullBoxRetrieverForTest extends FullAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever with Frs10xDormancyBoxRetriever

class AC36Spec extends UnitSpec  {

  "AC36" should {
    "for Abridged Accounts" when {
      val boxRetriever = mock[AbridgedBoxRetrieverForTest]
      "pass validation if all fields have a valid value" in {
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
        when(boxRetriever.ac16()).thenReturn(AC16(Some(16)))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(Some(20)))
        when(boxRetriever.ac28()).thenReturn(AC28(Some(28)))
        when(boxRetriever.ac30()).thenReturn(AC30(Some(30)))
        when(boxRetriever.ac34()).thenReturn(AC34(Some(34)))
        AC36(Some(10)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC16 field has a valid value" in {
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
        when(boxRetriever.ac16()).thenReturn(AC16(Some(16)))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(16)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC18 field has a valid value" in {
        when(boxRetriever.ac401()).thenReturn(AC401(None))
        when(boxRetriever.ac403()).thenReturn(AC403(None))

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(16)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is false" in {
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set()
      }
      "fail validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is true" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
      "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is false" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
      "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is true" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
    }
    "for Full Accounts" when {
      val boxRetriever = mock[FullBoxRetrieverForTest]
      "pass validation if all fields have a valid value" in {
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(Some(12)))
        when(boxRetriever.ac14()).thenReturn(AC14(Some(14)))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(Some(20)))
        when(boxRetriever.ac28()).thenReturn(AC28(Some(28)))
        when(boxRetriever.ac30()).thenReturn(AC30(Some(30)))
        when(boxRetriever.ac34()).thenReturn(AC34(Some(34)))
        AC36(Some(36)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if 1 field has a valid value" in {
        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(Some(12)))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(12)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if 1 (shared) field has a valid value" in {
        when(boxRetriever.ac401()).thenReturn(AC401(None))
        when(boxRetriever.ac403()).thenReturn(AC403(None))

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(18)).validate(boxRetriever) shouldBe empty
      }
      "fail validation if all current inputs are empty" in {
        when(boxRetriever.ac401()).thenReturn(AC401(None))
        when(boxRetriever.ac403()).thenReturn(AC403(None))

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
      "pass validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is false" in {
        when(boxRetriever.ac401()).thenReturn(AC401(None))
        when(boxRetriever.ac403()).thenReturn(AC403(None))

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set()
      }
      "fail validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is true" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
      "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is false" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
      "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is true" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }

      "pass validation if dormant where there would otherwise be an error" in {

        when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
        when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set.empty
      }
    }
  }
}
