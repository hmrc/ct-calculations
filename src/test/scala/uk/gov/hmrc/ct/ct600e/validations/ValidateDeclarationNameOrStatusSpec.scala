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

package uk.gov.hmrc.ct.ct600e.validations

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.E30
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class ValidateDeclarationNameOrStatusSpec extends WordSpec with MockitoSugar with Matchers with ValidateDeclarationNameOrStatus[CT600EBoxRetriever] {
    "ValidateDeclarationNameOrStatus validate" should {
      "not return error when all is good" in {
          val value = Some("test name")
          val mockBoxRetriever = mock[CT600EBoxRetriever]
          val box = E30(value)

          when(mockBoxRetriever.e30()).thenReturn(box)
      
          validateDeclarationNameOrStatus("E30", box) shouldBe Set()
        }
    
        "return error when is empty" in {
          val value: Option[String] = None
          val mockBoxRetriever = mock[CT600EBoxRetriever]
          val box = E30(value)

          when(mockBoxRetriever.e30()).thenReturn(box)

          validateDeclarationNameOrStatus("E30", box) shouldBe Set(CtValidation(Some("E30"), "error.E30.required"))
        }
    
        "return error when is too short" in {
          val value: Option[String] = Some("s")
          val mockBoxRetriever = mock[CT600EBoxRetriever]
          val box = E30(value)

          when(mockBoxRetriever.e30()).thenReturn(box)

          validateDeclarationNameOrStatus("E30", box) shouldBe Set(CtValidation(Some("E30"), "error.E30.text.sizeRange", Some(Seq("2", "56"))))
        }
    
        "return error when is too long" in {
          val value: Option[String] = Some("123456789 123456789 123456789 123456789 123456789 1234567")
          val mockBoxRetriever = mock[CT600EBoxRetriever]
          val box = E30(value)

          when(mockBoxRetriever.e30()).thenReturn(box)

          validateDeclarationNameOrStatus("E30", box) shouldBe Set(CtValidation(Some("E30"), "error.E30.text.sizeRange", Some(Seq("2", "56"))))
        }
    
        "return error when has invalid value" in {
          val value: Option[String] = Some("$%£")
          val mockBoxRetriever = mock[CT600EBoxRetriever]
          val box = E30(value)

          when(mockBoxRetriever.e30()).thenReturn(box)

          validateDeclarationNameOrStatus("E30", box) shouldBe Set(CtValidation(Some("E30"), "error.E30.regexFailure"))
        }
    }

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = ???
}
