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

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP980Spec extends WordSpec with Matchers with MockitoSugar {

  "CP980 validation" should {

    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(500)
      }


      val result = CP980(Some(1000)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP980"), "error.cp980.breakdown"))
    }

    " not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(250)
      }


      val result = CP980(Some(500)).validate(boxRetriever)

      result shouldBe Set.empty
    }


  }
}
