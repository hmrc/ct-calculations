package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP301, CP302, CP999, CPQ21}

trait TotalDonationsCalculator extends CtTypeConverters {

  def calculateTotalDonations(cpq21: CPQ21,
                              cp301: CP301,
                              cp302: CP302): CP999 = {

    val result: Int = cpq21.value match {
      case true => cp302 + cp301
      case false => 0
    }
    CP999(result)
  }
}
