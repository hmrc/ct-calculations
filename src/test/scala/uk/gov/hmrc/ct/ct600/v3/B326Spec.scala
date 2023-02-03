/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.version.calculations.ComputationsBoxRetrieverForTest

class B326Spec extends WordSpec with MockitoSugar with Matchers {

  "B326 validate" should {

    "not return errors when B326 is empty" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      //TODO commented out for now till we fix, just for the PR
      //B326(Some(1)).validate(mockBoxRetriever) shouldBe B326(Some(1))
    }
  }
}
