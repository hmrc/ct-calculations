package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesCarriedForwardsCalculator extends CtTypeConverters {

  def lossesCarriedForwardsCalculation(cpq17: CPQ17 = CPQ17(None),
                                       cpq19: CPQ19 = CPQ19(None),
                                       cpq20: CPQ20 = CPQ20(None),
                                       cp281: CP281,
                                       cp118: CP118,
                                       cp283: CP283,
                                       cp998: CP998,
                                       cp287: CP287,
                                       cato01: CATO01): CP288 =
    CP288(
      (cpq17.value, cpq19.value, cpq20.value) match {
        case (Some(true), None, None) => somePositiveOrZero(cp281 minus cp283)
        case (None, Some(false), None) => somePositiveOrZero(cp118.value)
        case (None, None, Some(false)) => somePositiveOrZero(cp118.value)
        case (None, Some(true), Some(true)) => somePositiveOrZero(cp118 - cp998.value.getOrElse(0) - cp287)
        case (None, Some(true), Some(false)) => somePositiveOrZero(cp118 - cato01)
        case (None, None, Some(true)) => somePositiveOrZero(cp118 - cp287)
        case _ => None
      }
    )

  private def somePositiveOrZero(calculation: Int): Option[Int] = Some(calculation max 0)
}


