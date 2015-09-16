package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP669(value: Option[Int]) extends CtBoxIdentifier(name = "Special rate pool written down value carried forward") with CtOptionalInteger

object CP669 extends Calculated[CP669, ComputationsBoxRetriever] with LowEmissionCarsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP669 = {

    calculateSpecialRatePoolWrittenDownValueCarriedForward(
      fieldValueRetriever.retrieveLEC01(),
      fieldValueRetriever.retrieveCPQ8(),
      fieldValueRetriever.retrieveCP666(),
      fieldValueRetriever.retrieveCP667(),
      fieldValueRetriever.retrieveCP668()
    )

  }

}