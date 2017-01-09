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

Error code: 9612 169 Location: [CTE]/InformationRequired/Expenditure/UKlandBuildings
Description: [E60] should exceed 0 if [E100] is present
Transactional error (en): Box E60 must be greater than 0 (zero) if Box E100 is completed
Transactional error (cy): Mae’n rhaid i Flwch E60 fod yn fwy na 0 (sero) os yw Blwch E100 wedi ei gwblhau

 */
class E60Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E60" should {
    "validate" when {
      "true when E60 == 0 and E100 is empty" in {
        when(boxRetriever.e60()).thenReturn(E60(Some(0)))
        when(boxRetriever.e100()).thenReturn(E100(None))
        E60(Some(0)).validate(boxRetriever) shouldBe Set.empty
      }
      "true when E60 == 0 and E100 == 0" in {
        when(boxRetriever.e60()).thenReturn(E60(Some(0)))
        when(boxRetriever.e100()).thenReturn(E100(Some(0)))
        E60(Some(0)).validate(boxRetriever) shouldBe Set.empty
      }
      "true when E60 > 0 and E100 > 0" in {
        when(boxRetriever.e60()).thenReturn(E60(Some(60)))
        when(boxRetriever.e100()).thenReturn(E100(Some(100)))
        E60(Some(60)).validate(boxRetriever) shouldBe Set.empty
      }
      "false when E60 == 0 and E100 > 0" in {
        when(boxRetriever.e60()).thenReturn(E60(Some(0)))
        when(boxRetriever.e100()).thenReturn(E100(Some(100)))
        E60(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.must.be.positive.when.E100.positive"))
      }
      "false when E60 empty and E100 > 0" in {
        when(boxRetriever.e60()).thenReturn(E60(None))
        when(boxRetriever.e100()).thenReturn(E100(Some(100)))
        E60(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.must.be.positive.when.E100.positive"))
      }
    }
  }
}
