/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7801Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter with MockFrs102AccountsRetriever {

  "AC301A" should {
    "be mandatory" in {
      AC7801(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7801"), "error.AC7801.required", None))
    }
  }
}
