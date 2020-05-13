

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP670(value: Option[Int]) extends CtBoxIdentifier(name = "Special rate pool balancing charge") with CtOptionalInteger

object CP670 extends Calculated[CP670, ComputationsBoxRetriever] with LowEmissionCarsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP670 = {

    calculateSpecialRatePoolBalancingCharge(
      fieldValueRetriever.cpQ8(),
      fieldValueRetriever.cp666(),
      fieldValueRetriever.cp667(),
      fieldValueRetriever.cpAux3()
    )

  }

}
