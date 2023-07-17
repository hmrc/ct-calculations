/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.AC403
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP981Spec extends AnyWordSpec with Matchers with MockitoSugar {
  "CP981 validation" should {
    "show correct error if under zero" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(Some(-1)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP981"), "error.CP981.mustBeZeroOrPositive"))
    }

    "CP981 can't be greater than CP983" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(10))
      }

      val result = CP981(Some(11)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP981"), "error.CP981.exceeds.CP983"))
    }

    "doesn't show error if zero" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(Some(0)).validate(boxRetriever)

      result shouldBe Set.empty
    }

    "doesn't show error if None" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(None).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
