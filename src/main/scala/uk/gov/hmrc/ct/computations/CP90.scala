

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP90(value: Option[Int]) extends CtBoxIdentifier(name = "Balance Allowance") with CtOptionalInteger

object CP90 extends Calculated[CP90, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(retriever: ComputationsBoxRetriever): CP90 =
    computeBalanceAllowance(
      retriever.cpQ8(),
      retriever.cp78(),
      retriever.cp84(),
      retriever.cp666(),
      retriever.cp673(),
      retriever.cp674(),
      retriever.cpAux1(),
      retriever.cpAux2(),
      retriever.cpAux3()
    )
}
