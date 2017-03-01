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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.FilingCompanyType
import uk.gov.hmrc.ct.accounts.AC205
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.{Frs105TestBoxRetriever, ValidateAssetsEqualSharesSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes

class AC491Spec extends ValidateAssetsEqualSharesSpec[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with MockitoSugar {

  val STANDARD_MIN = -99999999
  val STANDARD_MAX = 99999999
  
  override def addOtherBoxValue100Mock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac69()).thenReturn(AC69(Some(100)))

  override def addOtherBoxValueNoneMock(mockRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever) =
    when(mockRetriever.ac69()).thenReturn(AC69(None))

  testAssetsEqualToSharesValidation("AC491", AC491.apply)

  override def createMock(): Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever = {
    val boxRetrieverMock = mock[Frs105TestBoxRetriever]
    when(boxRetrieverMock.ac205()).thenReturn(AC205(Some(new LocalDate())))
    boxRetrieverMock
  }


  CompanyTypes.AllCompanyTypes.foreach { companyType =>
    s"be valid when is empty and there's no PY for company type: $companyType" in {
      val value = None
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(value).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC491"), "error.AC491.required"))
    }

    s"be invalid when is empty and there's PY for company type: $companyType" in {
      val value = None
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac205()).thenReturn(AC205(None))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(value).validate(boxRetriever) shouldBe empty
    }

    s"be valid when minimum for companyType: $companyType" in {
      val value = Some(STANDARD_MIN)
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(value).validate(boxRetriever) shouldBe empty
    }
    s"be valid when greater then min for companyType: $companyType" in {
      val value = Some(STANDARD_MIN + 1)
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(Some(STANDARD_MIN + 1)).validate(boxRetriever) shouldBe empty
    }
    s"be valid when positive but equals upper limit for companyType: $companyType" in {
      val value = Some(STANDARD_MAX)
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(Some(STANDARD_MAX)).validate(boxRetriever) shouldBe empty
    }
    s"fail validation when less then min lower limit for companyType: $companyType" in {
      val value = Some(STANDARD_MIN - 1)
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(Some(STANDARD_MIN - 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC491"), s"error.AC491.below.min", Some(Seq(STANDARD_MIN.toString, STANDARD_MAX.toString))))
    }
    s"fail validation when positive but above upper limit for companyType: $companyType" in {
      val value = Some(STANDARD_MAX + 1)
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))

      AC491(Some(STANDARD_MAX + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC491"), s"error.AC491.above.max", Some(Seq(STANDARD_MIN.toString, STANDARD_MAX.toString))))
    }

    s"pass validation when previous year has no value and box is empty for companyType: $companyType" in {
      val value = None
      val boxRetriever = createMock()
      when(boxRetriever.companyType()).thenReturn(FilingCompanyType(companyType))
      when(boxRetriever.ac69()).thenReturn(AC69(value))
      when(boxRetriever.ac205()).thenReturn(AC205(None))

      AC491(None).validate(boxRetriever) shouldBe empty
    }
  }
}
