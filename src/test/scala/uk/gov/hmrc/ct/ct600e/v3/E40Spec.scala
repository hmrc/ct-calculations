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
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

class E40Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter{

  val boxRetriever = mock[CT600EBoxRetriever]
  val NOW = DateHelper.now()
  val APEnd = NOW.minusMonths(1)

  before{
    when(boxRetriever.e4()).thenReturn(E4(APEnd))
  }

  "E40" should {
    "validate" when {
      "false when is empty" in {
        E40(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("E40"), errorMessageKey = "error.E40.required"))
      }

      val INVALID_DATE_ERROR_MESSAGE: Set[CtValidation] = Set(CtValidation(Some("E40"), s"error.E40.not.betweenInclusive", Some(Seq(toErrorArgsFormat(APEnd), toErrorArgsFormat(NOW)))))

      "false when date before AP end date" in {
        E40(Some(APEnd.minusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
      }
      "false when date after current date" in {
        E40(Some(NOW.plusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
      }
      "true when date is current date" in {
        E40(Some(NOW)).validate(boxRetriever) shouldBe Set()
      }
      "true when date is AP end date" in {
        E40(Some(APEnd)).validate(boxRetriever) shouldBe Set()
      }
      "true when date is between now and AP end date" in {
        E40(Some(APEnd.plusDays(1))).validate(boxRetriever) shouldBe Set()
      }
    }
  }

}
