/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA

import uk.gov.hmrc.ct.ct600.NumberRounding


case class SbaRate(numberOfDaysRate: Int, dailyRate: BigDecimal, rateYearlyPercentage: BigDecimal) extends NumberRounding {
  val rateYearlyPercentageAsInt = roundedToInt(rateYearlyPercentage * 100)
  val costRate = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}

case class SbaResults(ratePriorTaxYear2020: Option[SbaRate] = None, ratePostTaxYear2020: Option[SbaRate] = None) extends NumberRounding {
  val maybeTotalCodeBefore2020 =   ratePriorTaxYear2020.map(_.costRate).getOrElse(0)
  val maybeTotalCodeAfter2020 =   ratePostTaxYear2020.map(_.costRate).getOrElse(0)
  val totalCost: Option[Int] = Some(maybeTotalCodeBefore2020 + maybeTotalCodeAfter2020)
}
