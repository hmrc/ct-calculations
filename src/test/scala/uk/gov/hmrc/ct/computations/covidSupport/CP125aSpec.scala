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

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.UnitSpec
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.box.CtValidation

class CP125Spec extends UnitSpec with BoxValidationFixture[ComputationsBoxRetriever]{

  val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
  private val CP125Id = "CP125"
  override def setUpMocks(): Unit = {
    when(boxRetriever.cp121()) thenReturn CP121(Some(10))
  }

  testBoxIsZeroOrPositive(CP125Id, CP125.apply)

  "should pass validation" when {
    s"greater than 0 but less than $CP125Id" in {
      CP125(Some(5)).validate(boxRetriever) shouldBe validationSuccess
    }
  }
  "should fail validation" when {
    s"greater than 0 and greater than $CP125Id" in {
      CP125(Some(11)).validate(boxRetriever) shouldBe
        Set(CtValidation(Some(CP125Id), s"error.$CP125Id.exceeds.max", Some(Seq("10")))
      )
    }
  }
}
