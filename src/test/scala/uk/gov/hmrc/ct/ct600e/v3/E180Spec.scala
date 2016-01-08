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

package uk.gov.hmrc.ct.ct600e.v3

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

/*

Error code: 9613 174 Location: [CTE]/InformationRequired/Assets/InvestmentsOrLoans/Qualifying
Description: [E180] should not be present if [E185] is present
Transactional error (en): Box E180 must not be completed if Box E185 is completed.
Transactional error (cy): Ni ddylid cwblhau Blwch E180 os yw Blwch E185 wedi ei gwblhau.

 */
class E180Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[CT600EBoxRetriever]
  "E180" should {
    "validate when E185 has a value" when {
      "pass validation when E180 is False" in {
        when(boxRetriever.retrieveE180()).thenReturn(E180(Some(false)))
        when(boxRetriever.retrieveE185()).thenReturn(E185(Some(185.90)))
        E180.validate(boxRetriever) shouldBe Set.empty
      }
      "fail validation when E180 is true" in {
        when(boxRetriever.retrieveE180()).thenReturn(E180(Some(true)))
        when(boxRetriever.retrieveE185()).thenReturn(E185(Some(185.90)))
        E180.validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E180"), errorMessageKey = "error.E180.cannot.be.true.when.E185.has.value"))
      }
    }
    "validate when E185 is empty" when {
      "pass validation when E180 is False" in {
        when(boxRetriever.retrieveE180()).thenReturn(E180(Some(false)))
        when(boxRetriever.retrieveE185()).thenReturn(E185(None))
        E180.validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when E180 is True" in {
        when(boxRetriever.retrieveE180()).thenReturn(E180(Some(true)))
        when(boxRetriever.retrieveE185()).thenReturn(E185(None))
        E180.validate(boxRetriever) shouldBe Set.empty
      }
      "fail validation when E180 is None" in {
        when(boxRetriever.retrieveE180()).thenReturn(E180(None))
        when(boxRetriever.retrieveE185()).thenReturn(E185(None))
        E180.validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E180"), errorMessageKey = "error.E180.required"))
      }
    }
  }
}
