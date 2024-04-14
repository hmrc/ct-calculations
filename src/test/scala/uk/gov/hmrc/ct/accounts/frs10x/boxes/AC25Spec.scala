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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.MockFrs10xAccountsRetriever
import uk.gov.hmrc.ct.utils.UnitSpec

class AC25Spec extends UnitSpec with MockFrs10xAccountsRetriever {

  "AC25 validation" should {
    "validate successfully" when {
      "ac25 is a positive integer" in {
        AC25(Some(666)).validate(boxRetriever) shouldBe validationSuccess
      }
    }
    "fail validation" when {
      "ac25 is a negative integer" in {
        AC25(Some(-1)).validate(boxRetriever) shouldBe mustBeZeroOrPositiveError("AC25")
      }
    }
  }
}
