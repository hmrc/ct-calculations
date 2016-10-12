/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.accounts

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CompaniesHouseFiling
import uk.gov.hmrc.ct.accounts.frs102.ACQ8161
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


class ACQ8161Spec extends WordSpec with MockitoSugar with Matchers {

  "ACQ8161 validate" should {
    "return errors when filing is for CoHo and ACQ8161 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8161"), "error.ACQ8161.required"))
    }

    "not return errors when filing is for CoHo and ACQ8161 is true" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for CoHo and ACQ8161 is false" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is not for CoHo and ACQ8161 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set()
    }
  }
}
