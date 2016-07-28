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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5052ASpec extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[Frs10xAccountsBoxRetriever]

  "AC5052A" should {
    "pass validation when empty" in {

      AC5052A(None).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when int between 0 and 99999999" in {

      AC5052A(Some(6)).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when int is 0" in {

      AC5052A(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when int is 99999999" in {

      AC5052A(Some(99999999)).validate(boxRetriever) shouldBe Set.empty
    }
    "fail validation when int is bigger than 99999999" in {

      AC5052A(Some(99999999 + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052A"), "error.AC5052A.outOfRange", Some(List("0", "99999999"))))
    }
    "fail validation when int is negative" in {

      AC5052A(Some(-4)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052A"), "error.AC5052A.outOfRange", Some(List("0", "99999999"))))
    }
  }
}
