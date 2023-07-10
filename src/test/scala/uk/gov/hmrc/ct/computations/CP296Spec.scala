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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP296Spec extends AnyWordSpec with Matchers with MockitoSugar {

  "calculate" should {
      val testBuildingCost = 1001
      val testAPStart = new LocalDate("2019-04-01")

    "not include buildings with start date before AP start date" in {
      val testBuilding = Building(None, None, None, None, Some(testAPStart.minusDays(1)), None, Some(testBuildingCost), None)
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp1(): CP1 = CP1(testAPStart)
        override def sba01(): SBA01 = SBA01(List(testBuilding))
      }

      CP296.calculate(boxRetriever).value shouldBe Some(0)
    }

    "include buildings with start date on AP start date" in {
      val testBuilding = Building(None, None, None, None, Some(testAPStart), None, Some(testBuildingCost), None)
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp1(): CP1 = CP1(testAPStart)
        override def sba01(): SBA01 = SBA01(List(testBuilding))
      }

      CP296.calculate(boxRetriever).value shouldBe Some(testBuildingCost)
    }

    "include buildings with start date after AP start date" in {
      val testBuilding = Building(None, None, None, None, Some(testAPStart.plusDays(1)), None, Some(testBuildingCost), None)
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp1(): CP1 = CP1(testAPStart)
        override def sba01(): SBA01 = SBA01(List(testBuilding))
      }

      CP296.calculate(boxRetriever).value shouldBe Some(testBuildingCost)
    }
  }

}
