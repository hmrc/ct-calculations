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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes.{AC13 => FullAC13, _}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.{AC12, frs102, frs105}
import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling}


class ACQ8161Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  private trait Test105BoxRetriever extends Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever
  private trait Test102AbridgedBoxRetriever extends AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever
  private trait Test102FullBoxRetriever extends FullAccountsBoxRetriever with FilingAttributesBoxValueRetriever

  private var mockFrs105BoxRetriever: Test105BoxRetriever = _
  private var mockFrs102AbridgedBoxRetriever: Test102AbridgedBoxRetriever = _
  private var mockFrs102FullBoxRetriever: Test102FullBoxRetriever = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    mockFrs105BoxRetriever = mock[Test105BoxRetriever]

    when(mockFrs105BoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(mockFrs105BoxRetriever.ac12()).thenReturn(AC12(Some(10)))
    when(mockFrs105BoxRetriever.ac13()).thenReturn(AC13(Some(10)))
    when(mockFrs105BoxRetriever.ac405()).thenReturn(AC405(Some(10)))
    when(mockFrs105BoxRetriever.ac406()).thenReturn(AC406(Some(10)))
    when(mockFrs105BoxRetriever.ac410()).thenReturn(AC410(Some(10)))
    when(mockFrs105BoxRetriever.ac411()).thenReturn(AC411(Some(10)))
    when(mockFrs105BoxRetriever.ac415()).thenReturn(AC415(Some(10)))
    when(mockFrs105BoxRetriever.ac416()).thenReturn(AC416(Some(10)))
    when(mockFrs105BoxRetriever.ac420()).thenReturn(AC420(Some(10)))
    when(mockFrs105BoxRetriever.ac421()).thenReturn(AC421(Some(10)))
    when(mockFrs105BoxRetriever.ac425()).thenReturn(AC425(Some(10)))
    when(mockFrs105BoxRetriever.ac426()).thenReturn(AC426(Some(10)))
    when(mockFrs105BoxRetriever.ac34()).thenReturn(frs105.boxes.AC34(Some(10)))
    when(mockFrs105BoxRetriever.ac35()).thenReturn(frs105.boxes.AC35(Some(10)))

    mockFrs102AbridgedBoxRetriever = mock[Test102AbridgedBoxRetriever]

    when(mockFrs102AbridgedBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(mockFrs102AbridgedBoxRetriever.ac16()).thenReturn(AC16(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac17()).thenReturn(AC17(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac18()).thenReturn(AC18(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac19()).thenReturn(AC19(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac20()).thenReturn(AC20(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac21()).thenReturn(AC21(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac26()).thenReturn(AC26(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac27()).thenReturn(AC27(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac28()).thenReturn(AC28(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac29()).thenReturn(AC29(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac30()).thenReturn(AC30(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac31()).thenReturn(AC31(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac34()).thenReturn(frs102.boxes.AC34(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac35()).thenReturn(frs102.boxes.AC35(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac36()).thenReturn(AC36(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac37()).thenReturn(AC37(Some(10)))
    when(mockFrs102AbridgedBoxRetriever.ac5032()).thenReturn(AC5032(Some("asd")))

    mockFrs102FullBoxRetriever = mock[Test102FullBoxRetriever]

    when(mockFrs102FullBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(mockFrs102FullBoxRetriever.ac12()).thenReturn(AC12(Some(10)))
    when(mockFrs102FullBoxRetriever.ac13()).thenReturn(FullAC13(Some(10)))
    when(mockFrs102FullBoxRetriever.ac14()).thenReturn(AC14(Some(10)))
    when(mockFrs102FullBoxRetriever.ac15()).thenReturn(AC15(Some(10)))
    when(mockFrs102FullBoxRetriever.ac16()).thenReturn(AC16(Some(10)))
    when(mockFrs102FullBoxRetriever.ac17()).thenReturn(AC17(Some(10)))
    when(mockFrs102FullBoxRetriever.ac18()).thenReturn(AC18(Some(10)))
    when(mockFrs102FullBoxRetriever.ac19()).thenReturn(AC19(Some(10)))
    when(mockFrs102FullBoxRetriever.ac20()).thenReturn(AC20(Some(10)))
    when(mockFrs102FullBoxRetriever.ac21()).thenReturn(AC21(Some(10)))
    when(mockFrs102FullBoxRetriever.ac26()).thenReturn(AC26(Some(10)))
    when(mockFrs102FullBoxRetriever.ac27()).thenReturn(AC27(Some(10)))
    when(mockFrs102FullBoxRetriever.ac28()).thenReturn(AC28(Some(10)))
    when(mockFrs102FullBoxRetriever.ac29()).thenReturn(AC29(Some(10)))
    when(mockFrs102FullBoxRetriever.ac30()).thenReturn(AC30(Some(10)))
    when(mockFrs102FullBoxRetriever.ac31()).thenReturn(AC31(Some(10)))
    when(mockFrs102FullBoxRetriever.ac34()).thenReturn(frs102.boxes.AC34(Some(10)))
    when(mockFrs102FullBoxRetriever.ac35()).thenReturn(frs102.boxes.AC35(Some(10)))
    when(mockFrs102FullBoxRetriever.ac36()).thenReturn(AC36(Some(10)))
    when(mockFrs102FullBoxRetriever.ac37()).thenReturn(AC37(Some(10)))
    when(mockFrs102FullBoxRetriever.ac5032()).thenReturn(AC5032(Some("asd")))
  }

  "ACQ8161 validate" should {
    "return errors when filing is for CoHo and ACQ8161 is empty" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(None).validate(mockFrs105BoxRetriever) shouldBe Set(CtValidation(Some("ACQ8161"), "error.ACQ8161.required"))
    }

    "not return errors when filing is not CoHo and ACQ8161 is empty" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))

      ACQ8161(None).validate(mockFrs105BoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for CoHo and ACQ8161 is true" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockFrs105BoxRetriever) shouldBe Set()
    }

    "not return errors when filing is Joint and ACQ8161 is true" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockFrs105BoxRetriever) shouldBe Set()
    }

    "not return errors when filing is CoHo only and ACQ8161 is false (frs105 variant) " in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs105BoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockFrs105BoxRetriever.ac12()).thenReturn(AC12(None))
      when(mockFrs105BoxRetriever.ac13()).thenReturn(AC13(None))
      when(mockFrs105BoxRetriever.ac405()).thenReturn(AC405(None))
      when(mockFrs105BoxRetriever.ac406()).thenReturn(AC406(None))
      when(mockFrs105BoxRetriever.ac410()).thenReturn(AC410(None))
      when(mockFrs105BoxRetriever.ac411()).thenReturn(AC411(None))
      when(mockFrs105BoxRetriever.ac415()).thenReturn(AC415(None))
      when(mockFrs105BoxRetriever.ac416()).thenReturn(AC416(None))
      when(mockFrs105BoxRetriever.ac420()).thenReturn(AC420(None))
      when(mockFrs105BoxRetriever.ac421()).thenReturn(AC421(None))
      when(mockFrs105BoxRetriever.ac425()).thenReturn(AC425(None))
      when(mockFrs105BoxRetriever.ac426()).thenReturn(AC426(None))
      when(mockFrs105BoxRetriever.ac34()).thenReturn(frs105.boxes.AC34(None))
      when(mockFrs105BoxRetriever.ac35()).thenReturn(frs105.boxes.AC35(None))

      ACQ8161(Some(false)).validate(mockFrs105BoxRetriever) shouldBe Set()
    }

    "not return errors when filing is CoHo only and ACQ8161 is false (frs102 abridged variant)" in {
      when(mockFrs102AbridgedBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs102AbridgedBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockFrs102AbridgedBoxRetriever.ac16()).thenReturn(AC16(None))
      when(mockFrs102AbridgedBoxRetriever.ac17()).thenReturn(AC17(None))
      when(mockFrs102AbridgedBoxRetriever.ac18()).thenReturn(AC18(None))
      when(mockFrs102AbridgedBoxRetriever.ac19()).thenReturn(AC19(None))
      when(mockFrs102AbridgedBoxRetriever.ac20()).thenReturn(AC20(None))
      when(mockFrs102AbridgedBoxRetriever.ac21()).thenReturn(AC21(None))
      when(mockFrs102AbridgedBoxRetriever.ac26()).thenReturn(AC26(None))
      when(mockFrs102AbridgedBoxRetriever.ac27()).thenReturn(AC27(None))
      when(mockFrs102AbridgedBoxRetriever.ac28()).thenReturn(AC28(None))
      when(mockFrs102AbridgedBoxRetriever.ac29()).thenReturn(AC29(None))
      when(mockFrs102AbridgedBoxRetriever.ac30()).thenReturn(AC30(None))
      when(mockFrs102AbridgedBoxRetriever.ac31()).thenReturn(AC31(None))
      when(mockFrs102AbridgedBoxRetriever.ac34()).thenReturn(frs102.boxes.AC34(None))
      when(mockFrs102AbridgedBoxRetriever.ac35()).thenReturn(frs102.boxes.AC35(None))
      when(mockFrs102AbridgedBoxRetriever.ac36()).thenReturn(AC36(None))
      when(mockFrs102AbridgedBoxRetriever.ac37()).thenReturn(AC37(None))
      when(mockFrs102AbridgedBoxRetriever.ac5032()).thenReturn(AC5032(None))

      ACQ8161(Some(false)).validate(mockFrs102AbridgedBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is Joint and ACQ8161 is false" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs105BoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))

      ACQ8161(Some(false)).validate(mockFrs105BoxRetriever) shouldBe Set()
    }

    "return 'cannot exist' error when filing is for FRS105 for CoHo Only and ACQ8161 is false" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs105BoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))

      ACQ8161(Some(false)).validate(mockFrs105BoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "return 'cannot exist' error when filing is for FRS105 for CoHo Only and ACQ8161 is false with AC12 and AC13 only populated." in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs105BoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockFrs105BoxRetriever.ac405()).thenReturn(AC405(None))
      when(mockFrs105BoxRetriever.ac406()).thenReturn(AC406(None))
      when(mockFrs105BoxRetriever.ac410()).thenReturn(AC410(None))
      when(mockFrs105BoxRetriever.ac411()).thenReturn(AC411(None))
      when(mockFrs105BoxRetriever.ac415()).thenReturn(AC415(None))
      when(mockFrs105BoxRetriever.ac416()).thenReturn(AC416(None))
      when(mockFrs105BoxRetriever.ac420()).thenReturn(AC420(None))
      when(mockFrs105BoxRetriever.ac421()).thenReturn(AC421(None))
      when(mockFrs105BoxRetriever.ac425()).thenReturn(AC425(None))
      when(mockFrs105BoxRetriever.ac426()).thenReturn(AC426(None))
      when(mockFrs105BoxRetriever.ac34()).thenReturn(frs105.boxes.AC34(None))
      when(mockFrs105BoxRetriever.ac35()).thenReturn(frs105.boxes.AC35(None))

      ACQ8161(Some(false)).validate(mockFrs105BoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "return 'cannot exist' error when filing is for FRS102 full for CoHo Only and ACQ8161 is false" in {
      when(mockFrs102FullBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs102FullBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))

      ACQ8161(Some(false)).validate(mockFrs102FullBoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "return 'cannot exist' error when filing is for FRS102 full for CoHo Only and ACQ8161 is false with only non abridged fields populated" in {
      when(mockFrs102FullBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs102FullBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockFrs102FullBoxRetriever.ac16()).thenReturn(AC16(None))
      when(mockFrs102FullBoxRetriever.ac17()).thenReturn(AC17(None))
      when(mockFrs102FullBoxRetriever.ac18()).thenReturn(AC18(None))
      when(mockFrs102FullBoxRetriever.ac19()).thenReturn(AC19(None))
      when(mockFrs102FullBoxRetriever.ac20()).thenReturn(AC20(None))
      when(mockFrs102FullBoxRetriever.ac21()).thenReturn(AC21(None))
      when(mockFrs102FullBoxRetriever.ac26()).thenReturn(AC26(None))
      when(mockFrs102FullBoxRetriever.ac27()).thenReturn(AC27(None))
      when(mockFrs102FullBoxRetriever.ac28()).thenReturn(AC28(None))
      when(mockFrs102FullBoxRetriever.ac29()).thenReturn(AC29(None))
      when(mockFrs102FullBoxRetriever.ac30()).thenReturn(AC30(None))
      when(mockFrs102FullBoxRetriever.ac31()).thenReturn(AC31(None))
      when(mockFrs102FullBoxRetriever.ac34()).thenReturn(frs102.boxes.AC34(None))
      when(mockFrs102FullBoxRetriever.ac35()).thenReturn(frs102.boxes.AC35(None))
      when(mockFrs102FullBoxRetriever.ac36()).thenReturn(AC36(None))
      when(mockFrs102FullBoxRetriever.ac37()).thenReturn(AC37(None))
      when(mockFrs102FullBoxRetriever.ac5032()).thenReturn(AC5032(None))


      ACQ8161(Some(false)).validate(mockFrs102FullBoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "return 'cannot exist' error when filing is for FRS102 Abridged for CoHo Only and ACQ8161 is false" in {
      when(mockFrs102AbridgedBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockFrs102AbridgedBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))

      ACQ8161(Some(false)).validate(mockFrs102AbridgedBoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "not return errors when filing is not for CoHo and ACQ8161 is empty" in {
      when(mockFrs105BoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))

      ACQ8161(None).validate(mockFrs105BoxRetriever) shouldBe Set()
    }
  }
}
