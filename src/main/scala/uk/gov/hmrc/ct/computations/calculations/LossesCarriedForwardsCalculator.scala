/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesCarriedForwardsCalculator extends CtTypeConverters {

  def lossesCarriedForwardsCalculation(cp281: CP281,
                                       cp118: CP118,
                                       cp283: CP283,
                                       cp998: CP998,
                                       cp287: CP287,
                                       cp997: CP997,
                                       cp997d: CP997d,
                                       cp997c: CP997c
                                      ): CP288 = {
    val finalLike997 = if(cp997.hasValue)
      cp997.orZero
    else
      cp997c + cp997d
    CP288(Some((cp281 + cp118 - cp283 - cp998 - cp287 - finalLike997) max 0))
  }

}
