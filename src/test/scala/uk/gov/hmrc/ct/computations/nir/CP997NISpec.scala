package uk.gov.hmrc.ct.computations.nir

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.stubs.NorthernIrelandStubbedComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3._


class CP997NISpec extends WordSpec with Matchers with MockitoSugar with NorthernIrelandRateValidation {


    def makeBoxRetriever(cpq117Value: Option[Boolean] = Some(true), cato01Value: Int = 1000, cp997dValue: Option[Int] = Some(200), cp997eValue: Option[Int] = Some(200), b5Value: Option[Boolean] = Some(true)) = {

      val retriever = new NorthernIrelandStubbedComputationsBoxRetriever {
        override def cato01(): CATO01 = CATO01(cato01Value)
        override def cpQ117(): CPQ117 = CPQ117(cpq117Value)
        override def b5(): B5 = B5(b5Value)
        override def cp997d(): CP997d = CP997d(cp997dValue)
        override def cp997e(): CP997e = CP997e(cp997eValue)

      }
      retriever
    }

  "CP997NI" should {

    "be cp997d + cp997e if CPQ117 = true, b5 = true and value for CP997d and e are there" in {
      CP997NI.calculate(makeBoxRetriever()) shouldBe CP997NI(Some(400))
    }
    "be cp997d + cp997e if CPQ117 = false, b5 = true and value for CP997d and e are there" in {
      CP997NI.calculate(makeBoxRetriever(Some(false), 1000, Some(200), Some(200), Some(true))) shouldBe CP997NI(Some(400))
    }
    "be cp997d + cp997e CPQ117 = true, b5 = false and value for CP997d and e are there." in {
      CP997NI.calculate(makeBoxRetriever(Some(true), 1000, Some(200), Some(200), Some(false))) shouldBe CP997NI(Some(400))
    }
    "be  cp997d if CPQ117 = false, b5 = false and value for CP997d and e isn't there." in {
      CP997NI.calculate(makeBoxRetriever(Some(true), 1000, Some(200), Some(0), Some(false))) shouldBe CP997NI(Some(200))
    }
    "be Some(0) if CPQ117 = false, b5 = false and value of CP997d and CP997e aren't there" in {
      CP997NI.calculate(makeBoxRetriever(Some(true), 1000, None, None, Some(false))) shouldBe CP997NI(Some(0))
    }
    "be None if CATO01 = 0"in {
      CP997NI.calculate(makeBoxRetriever(Some(true), -1, None, None, Some(false))) shouldBe CP997NI(None)

    }
  }

}
