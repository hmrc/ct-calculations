/*
 * Copyright 2024 HM Revenue & Customs
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
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP982Spec extends AnyWordSpec with Matchers with MockitoSugar {

  "CP982 validation" should {

    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(1000)

        override def cp981 = CP981(500)

        override def cp980 = CP980(500)
      }

      val result = CP982(Some(500)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP982"), "error.cp982.breakdown", Some(List("0"))))
    }

    " not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(1000)

        override def cp981 = CP981(250)

        override def cp980 = CP980(250)
      }


      val result = CP982(Some(500)).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
