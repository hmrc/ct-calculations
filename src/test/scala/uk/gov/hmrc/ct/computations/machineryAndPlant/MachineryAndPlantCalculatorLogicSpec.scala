package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.utils.UnitSpec
import org.mockito.Mockito._
import uk.gov.hmrc.ct.computations.{CP79, CP82, CP87, CPAux1, CPAux2}

class MachineryAndPlantCalculatorLogicSpec extends UnitSpec {


  "MachineryAndPlantCalculatorLogic" should {
    val number1 = 5000
    val number2 = 3000

    "calculate the value for CP94 successfully" in {
      when(mockComputationsBoxRetriever.cpAux1()) thenReturn CPAux1(number1)
      when(mockComputationsBoxRetriever.cp79()) thenReturn CP79(Some(number2))

      CP94.calculate(mockComputationsBoxRetriever) shouldBe CP94(8000)
    }
    "calculate the value for CP97 successfully" in {
      when(mockComputationsBoxRetriever.cp87()) thenReturn CP87(number2)
      when(mockComputationsBoxRetriever.cp94()) thenReturn CP94(number1)

      CP97.calculate(mockComputationsBoxRetriever) shouldBe CP97(2000)
    }

    "calculate the value for CP105 successfully" in {
      when(mockComputationsBoxRetriever.cpAux2()) thenReturn CPAux2(number1)
      when(mockComputationsBoxRetriever.cp82()) thenReturn CP82(number2)

      CP105.calculate(mockComputationsBoxRetriever) shouldBe CP105(8000)
    }
  }


}
