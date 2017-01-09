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

package uk.gov.hmrc.ct.accounts.validation

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import play.api.data.validation.ValidationError
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.domain.CompanyTypes

trait Frs102TestBoxRetriever extends Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever

trait Frs105TestBoxRetriever extends Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever

trait ValidateAssetsEqualSharesSpec[T <: FilingAttributesBoxValueRetriever] extends WordSpec with Matchers with MockitoSugar {

  def addOtherBoxValue100Mock(mockRetriever: T): Unit

  def addOtherBoxValueNoneMock(mockRetriever: T): Unit

  def createMock(): T

  def testAssetsEqualToSharesValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[T]): Unit = {

    s"$boxId" should {
      "for NON limited by guarantee company" when {
        val noLimitedByGuaranteeCompanies = CompanyTypes.AllCompanyTypes.filterNot(CompanyTypes.LimitedByGuaranteeCompanyTypes.contains)
        noLimitedByGuaranteeCompanies.foreach { companyType =>
          s"return an error if it has a different value to other box for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValue100Mock(retriever)

            builder(Some(50)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
          }

          s"return an error if it has a value and other box is None for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValueNoneMock(retriever)

            builder(Some(100)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
          }

          s"return no error if the box is not mandatory and both value are None for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValueNoneMock(retriever)

            val outcome:Set[CtValidation] = builder(None).validate(retriever)
            def boxIsNotRequired = !outcome.exists(_.errorMessageKey.contains("required"))
            if(boxIsNotRequired) {
              outcome shouldBe empty
            } else {
              outcome.size shouldBe 1
            }
          }

          s"return no error if they have the same values for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValue100Mock(retriever)

            builder(Some(100)).validate(retriever) shouldBe empty
          }
        }
      }

      "for limited by guarantee company" when {
        CompanyTypes.LimitedByGuaranteeCompanyTypes.foreach { companyType =>
          s"return an error if it has a different value to other box for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValue100Mock(retriever)

            builder(Some(50)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToMembersFunds"))
          }

          s"return an error if it has a value and other box is None for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValueNoneMock(retriever)

            builder(Some(100)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToMembersFunds"))
          }

          s"return no error if the box is not mandatory and if both value are None for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValueNoneMock(retriever)

            val outcome:Set[CtValidation] = builder(None).validate(retriever)
            def boxIsNotRequired = !outcome.exists(_.errorMessageKey.contains("required"))
            if(boxIsNotRequired) {
              outcome shouldBe empty
            } else {
              outcome.size shouldBe 1
            }
          }

          s"return no error if they have the same values for companyType: $companyType" in {
            val retriever = createMock()
            when(retriever.companyType()).thenReturn(FilingCompanyType(companyType))

            addOtherBoxValue100Mock(retriever)

            builder(Some(100)).validate(retriever) shouldBe empty
          }
        }
      }
    }
  }
}
