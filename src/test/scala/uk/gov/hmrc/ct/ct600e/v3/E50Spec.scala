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

package uk.gov.hmrc.ct.ct600e.v3

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

/*

Error code: 9611 168 Location: [CTE]/InformationRequired/Expenditure/TradingCosts
Description: [E50] should exceed 0 if [E95] is present
Transactional error (en): Box E50 must be greater than 0 (zero) if Box E95 is completed
Transactional error (cy): Mae’n rhaid i Flwch E50 fod yn fwy na 0 (sero) os yw Blwch E95 wedi ei gwblhau

 */
class E50Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E50" should {
    "validate" when {
      "true when E50 == 0 and E95 is empty" in {
        when(boxRetriever.e50()).thenReturn(E50(Some(0)))
        when(boxRetriever.e95()).thenReturn(E95(None))
        E50(Some(0)).validate(boxRetriever) shouldBe Set.empty
      }
      "true when E50 == 0 and E95 == 0" in {
        when(boxRetriever.e50()).thenReturn(E50(Some(0)))
        when(boxRetriever.e95()).thenReturn(E95(Some(0)))
        E50(Some(0)).validate(boxRetriever) shouldBe Set.empty
      }
      "true when E50 > 0 and E95 > 0" in {
        when(boxRetriever.e50()).thenReturn(E50(Some(60)))
        when(boxRetriever.e95()).thenReturn(E95(Some(100)))
        E50(Some(60)).validate(boxRetriever) shouldBe Set.empty
      }
      "false when E50 == 0 and E95 > 0" in {
        when(boxRetriever.e50()).thenReturn(E50(Some(0)))
        when(boxRetriever.e95()).thenReturn(E95(Some(100)))
        E50(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.must.be.positive.when.E95.positive"))
      }
      "false when E50 empty and E95 > 0" in {
        when(boxRetriever.e50()).thenReturn(E50(None))
        when(boxRetriever.e95()).thenReturn(E95(Some(100)))
        E50(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.must.be.positive.when.E95.positive"))
      }
    }
  }
}
