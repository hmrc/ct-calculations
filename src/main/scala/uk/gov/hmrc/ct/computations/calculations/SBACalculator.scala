/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA.{SbaRate, SbaResults}
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper


trait SBACalculator extends NumberRounding with AccountingPeriodHelper {

  private type SBARateForTaxYear = BigDecimal // might not get much value out of this
  private val endOfTaxYear2019 = LocalDate.parse("2020-03-31")

  val ratePriorTy2020: SBARateForTaxYear = 0.02
  val rateAfterTy2020: SBARateForTaxYear = 0.03

  def getDaysIntheYear(apStartDate: LocalDate): Int = {
    val yearAfterApStart = apStartDate.plusYears(1)
    if (apStartDate.getDayOfMonth == yearAfterApStart.getDayOfMonth) daysBetween(apStartDate, yearAfterApStart) - 1 else 366
  }

  def apportionedCostOfBuilding(cost: BigDecimal, daysInTheYear: Int, rateForTy: SBARateForTaxYear): BigDecimal = (cost * rateForTy) / daysInTheYear

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  def getSbaDetails(apStartDate: LocalDate, apEndDate: LocalDate, maybeFirstUsageDate: Option[LocalDate], maybeCost: Option[Int]): Option[SbaResults] = {

    (maybeFirstUsageDate, maybeCost) match {
      case (Some(firstUsageDate), Some(cost)) => {

        val daysInTheYear = getDaysIntheYear(apStartDate)

        val isAfterTy2020 = financialYearForDate(apEndDate) >= 2020

        val dailyRateAfter2020 = apportionedCostOfBuilding(cost, daysInTheYear, rateAfterTy2020)
        val dailyRateBefore2020 = apportionedCostOfBuilding(cost, daysInTheYear, ratePriorTy2020)


        val firstDayToApplySbaTax = if(isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate)) firstUsageDate else apStartDate
        val sbaResult: Option[SbaResults] = isAfterTy2020 match {
          case true => apportioningRateAfterTaxYear2020(firstDayToApplySbaTax, apEndDate, dailyRateAfter2020, dailyRateBefore2020)
          case false => Some(SbaResults(ratePriorTaxYear2020 = Some(SbaRate(daysBetween(firstDayToApplySbaTax, apEndDate), dailyRateBefore2020, ratePriorTy2020))))
        }
        sbaResult
        }
      case _ => None
      }
  }

  private def apportioningRateAfterTaxYear2020(daysToApplyTax: LocalDate,
                                apEndDate: LocalDate,
                                dailyRateAfter2020: BigDecimal,
                                dailyRateBefore2020: BigDecimal): Option[SbaResults] = {
    val splitRate: Boolean = daysToApplyTax.isBefore(endOfTaxYear2019) && apEndDate.isAfter(endOfTaxYear2019)
    val totalDaysCharged: Int = daysBetween(daysToApplyTax, apEndDate)

    if(splitRate){
      val daysBefore2020 =  daysBetween(daysToApplyTax, endOfTaxYear2019)
      val daysAfter2020 = totalDaysCharged - daysBetween(daysToApplyTax, endOfTaxYear2019)
      val sbaRateAfter2020 = SbaRate(daysAfter2020, dailyRateAfter2020, rateAfterTy2020)
      val sbaRatePrior2020 = SbaRate(daysBefore2020, dailyRateBefore2020, ratePriorTy2020)
      Some(SbaResults(Some(sbaRatePrior2020),     Some(sbaRateAfter2020)))
    }else {
      Some(SbaResults(None, Some(SbaRate(totalDaysCharged, dailyRateAfter2020, rateAfterTy2020))))
    }

  }

  //we can also use cats to make this look better.
  def sumAmount(xs: List[Option[Int]]): Option[Int] = {
    xs.fold(Option(0)) {
      (accumulator, element) =>
        for {
          accum <- accumulator
          ele <- element
        } yield accum + ele
    }
  }
}
