/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.CtTypeConverters

trait MachineryAndPlantCalculationsLogic extends CtTypeConverters {
  def sumOf(boxValues: Int*) = boxValues.sum
}
