/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.Validators

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait DonationsValidationFixture extends WordSpec with Matchers with MockitoSugar {

  def testGlobalDonationsValidationErrors(box: ValidatableBox[ComputationsBoxRetriever])(boxRetriever: ComputationsBoxRetriever): Unit = {
    when(boxRetriever.cp301()).thenReturn(CP301(1))
    when(boxRetriever.cp302()).thenReturn(CP302(1))


    "fail if total donations in p&l is undefined and the sum of donations is greater than 0" in {
      when(boxRetriever.cp29()).thenReturn(CP29(None))
      when(boxRetriever.cato13()).thenReturn(CATO13(10))

      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cp303()).thenReturn(CP303(0))
      when(boxRetriever.cp3030()).thenReturn(CP3030(0))

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))

      when(boxRetriever.cp999()).thenReturn(CP999(0))
      when(boxRetriever.cp303()).thenReturn(CP303(1))
      when(boxRetriever.cp3030()).thenReturn(CP3030(0))

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))

      when(boxRetriever.cp999()).thenReturn(CP999(0))
      when(boxRetriever.cp303()).thenReturn(CP303(0))
      when(boxRetriever.cp3030()).thenReturn(CP3030(1))

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))
    }

    "validate if total donations in p&l is undefined the sum of donations is 0" in {
      when(boxRetriever.cp29()).thenReturn(CP29(None))
      when(boxRetriever.cp999()).thenReturn(CP999(0))
      when(boxRetriever.cp303()).thenReturn(CP303(0))
      when(boxRetriever.cp3030()).thenReturn(CP3030(0))

      box.validate(boxRetriever) shouldBe Set.empty
    }

    "fail if total donations is defined and the sum of donations is greater" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(3)))
      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cp303()).thenReturn(CP303(2))
      when(boxRetriever.cp3030()).thenReturn(CP3030(1))

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))
    }

    "validate if total donations is defined and the sum of donations is less than or equal" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(3)))
      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cp303()).thenReturn(CP303(1))
      when(boxRetriever.cp3030()).thenReturn(CP3030(1))

      box.validate(boxRetriever) shouldBe Set.empty

      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cp303()).thenReturn(CP303(1))
      when(boxRetriever.cp3030()).thenReturn(CP3030(None))

      box.validate(boxRetriever) shouldBe Set.empty
    }


    "validate if total donations is less than or equal to Net Profit" in {
      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cato13()).thenReturn(CATO13(1))

      box.validate(boxRetriever) shouldBe Set.empty

      when(boxRetriever.cp999()).thenReturn(CP999(1))
      when(boxRetriever.cato13()).thenReturn(CATO13(2))

      box.validate(boxRetriever) shouldBe Set.empty
    }


    "fail if total donations is greater than Net Profit" in {

      when(boxRetriever.cp999()).thenReturn(CP999(2))
      when(boxRetriever.cato13()).thenReturn(CATO13(1))

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.qualifying.donations.exceeds.net.profit"))
    }
  }
}
