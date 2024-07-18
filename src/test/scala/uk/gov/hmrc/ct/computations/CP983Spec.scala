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

import java.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs10x.boxes.ACQ8999
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO24}

class CP983Spec extends AnyWordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever]
{
  trait StubComputationsBoxRetriever extends ComputationsBoxRetriever{
    self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>
}
  override val boxRetriever = mock[StubComputationsBoxRetriever]

  "CP983 validation" should {
    "mandatory" in {
      when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
      when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
      when(boxRetriever.cp7()).thenReturn(CP7(None))
      when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
      when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
      val cp983 = CP983(None)
      cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.required"))
    }

    "not mandatory if ACQ8999 is true" in {
      when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
      when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
      when(boxRetriever.cp7()).thenReturn(CP7(None))
      when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
      when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(true)))
      val cp983 = CP983(None)
      cp983.validate(boxRetriever) shouldBe Set.empty
    }
     "not mandatory if CATO24 is false" in {
       when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
       when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
       when(boxRetriever.cp7()).thenReturn(CP7(None))
       when(boxRetriever.cato24()).thenReturn(CATO24(Some(false)))
       when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
       val cp983 = CP983(None)
       cp983.validate(boxRetriever) shouldBe Set.empty
     }

     "Can't be negative" in {
       when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
       when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
       when(boxRetriever.cp7()).thenReturn(CP7(None))
       when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
       when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
       val cp983 = CP983(Some(-1))
       cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.mustBeZeroOrPositive"))
     }

   "Can't be more than 632000" in {
     when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
     when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
     when(boxRetriever.cp7()).thenReturn(CP7(None))
     when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
     when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
     val cp983 = CP983(Some(632001))

     cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.above.max", Some(List("-632,000", "632,000"))))

   }

   "Can't be more than 632000 with CP7" in {
     when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
     when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
     when(boxRetriever.cp7()).thenReturn(CP7(Some(2)))
     when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
     when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
     val cp983 = CP983(Some(631999))

     cp983.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP983"), "error.CP983.above.max", Some(List("-632,000", "632,000"))))

   }
   "No errors for value under 632000" in {
     when(boxRetriever.cp1()).thenReturn(CP1(LocalDate.parse("2019-01-01")))
     when(boxRetriever.cp2()).thenReturn(CP2(LocalDate.parse("2019-12-31")))
     when(boxRetriever.cp7()).thenReturn(CP7(None))
     when(boxRetriever.cato24()).thenReturn(CATO24(Some(true)))
     when(boxRetriever.acq8999a()).thenReturn(ACQ8999(Some(false)))
     val cp983 = CP983(Some(632000))

     cp983.validate(boxRetriever) shouldBe Set.empty
   }
  }
}
