package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP253, CP81, CP82, CP87}

trait QualifyingExpenditureOnMachineryCalculation extends CtTypeConverters {

  def qualifyingExpenditureCalculation(cp82: CP82,
                                       cp87: CP87,
                                       cp81: CP81): CP253 = {

    val result = cp82.value match {
      case Some(qualifying) if qualifying > 0 => qualifying + cp81 - cp87
      case _ => 0
    }
    CP253(result)
  }
}
