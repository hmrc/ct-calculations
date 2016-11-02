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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.MockFrs105AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.ValidateAssetsEqualSharesSpec
import uk.gov.hmrc.ct.box.CtValidation

class AC490Spec extends ValidateAssetsEqualSharesSpec with MockFrs105AccountsRetriever {

  val STANDARD_MIN = -99999999
  val STANDARD_MAX = 99999999
  
  override def addOtherBoxValue100Mock(mockRetriever: Frs105AccountsBoxRetriever) =
    when(mockRetriever.ac68()).thenReturn(AC68(Some(100)))

  override def addOtherBoxValueNoneMock(mockRetriever: Frs105AccountsBoxRetriever) =
    when(mockRetriever.ac68()).thenReturn(AC68(None))

  testAssetsEqualToSharesValidation("AC490", AC490.apply)

  "be valid when minimum" in {
    val value = Some(STANDARD_MIN)
    when(boxRetriever.ac68()).thenReturn(AC68(value))

    AC490(value).validate(boxRetriever) shouldBe empty
  }
  "be valid when greater then min" in {
    val value = Some(STANDARD_MIN + 1)
    when(boxRetriever.ac68()).thenReturn(AC68(value))

    AC490(Some(STANDARD_MIN + 1)).validate(boxRetriever) shouldBe empty
  }
  "be valid when positive but equals upper limit" in {
    val value = Some(STANDARD_MAX)
    when(boxRetriever.ac68()).thenReturn(AC68(value))

    AC490(Some(STANDARD_MAX)).validate(boxRetriever) shouldBe empty
  }
  "fail validation when less then min lower limit" in {
    val value = Some(STANDARD_MIN - 1)
    when(boxRetriever.ac68()).thenReturn(AC68(value))

    AC490(Some(STANDARD_MIN - 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC490"), s"error.AC490.below.min", Some(Seq(STANDARD_MIN.toString, STANDARD_MAX.toString))))
  }
  "fail validation when positive but above upper limit" in {
    val value = Some(STANDARD_MAX + 1)
    when(boxRetriever.ac68()).thenReturn(AC68(value))

    AC490(Some(STANDARD_MAX + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC490"), s"error.AC490.above.max", Some(Seq(STANDARD_MIN.toString, STANDARD_MAX.toString))))
  }

}
