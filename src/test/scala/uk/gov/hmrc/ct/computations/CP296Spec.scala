

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP296Spec extends WordSpec with Matchers with MockitoSugar {

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
