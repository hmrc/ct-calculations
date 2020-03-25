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

package uk.gov.hmrc.ct.accounts.validation

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.AC402
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC402Spec extends WordSpec with MockitoSugar with Matchers {

  "AC402 validation" should {
    val boxRetriever = mock[AccountsBoxRetriever]
    "not show error messages where AC402 is within limit" in {

      AC402(Some(0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC402(Some(999999)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC402(None).validate(boxRetriever) shouldBe Set.empty[CtValidation]

    }

    "show correct error messages where AC402 is outside limit" in {

      AC402(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC402"),"error.AC402.mustBeZeroOrPositive",None))
      AC402(Some(-0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC402(Some(1000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC402"),"error.AC402.exceeds.max",Some(List("999999"))))


    }


  }

}