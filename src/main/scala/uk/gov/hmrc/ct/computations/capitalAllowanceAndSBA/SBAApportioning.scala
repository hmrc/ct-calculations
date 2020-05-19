package uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA

import uk.gov.hmrc.ct.ct600.NumberRounding

case class SBARate(numberOfDaysRate: Int, dailyRate: BigDecimal, rateYearlyPercentage: BigDecimal) extends NumberRounding {

  val costRate = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}

case class SBAResults(ratePriorTaxYear2020: SBARate, ratePostTaxYear2020: Option[SBARate] = None) extends NumberRounding {

  val totalCost: Option[Int] = Some(ratePriorTaxYear2020.costRate + ratePostTaxYear2020.map(_.costRate).getOrElse(0))
}
