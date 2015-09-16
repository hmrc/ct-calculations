package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.CATO02
import uk.gov.hmrc.ct.computations.{CP1, CP2}

import scala.math.BigDecimal.RoundingMode

trait AnnualInvestmentAllowanceCalculator {

  def maximum(cp1: CP1, cp2: CP2, allowableAmounts: Set[AnnualInvestmentAllowancePeriod]) : CATO02 = {
    val result = allowableAmounts.map { investmentAllowancePeriod =>
      val daysInPeriod = investmentAllowancePeriod.intersectingDays(cp1.value, cp2.value)
      val allowanceThisPeriod = BigDecimal(daysInPeriod) * (BigDecimal(investmentAllowancePeriod.maximumAllowed) / yearLengthForApportioning(cp1.value, cp2.value))

      allowanceThisPeriod.setScale(0, RoundingMode.UP).toInt
    }.sum

    CATO02(result)
  }

  //Apportion by length of AP unless covers an AIA Period Start date with it's date range, in which case we always apportion by 365
  //This is to give the benefit of the calculation to the client when they cover a change in allowance on a leap year
  protected def yearLengthForApportioning(apStartDate: LocalDate, apEndDate: LocalDate): Int = {
    val standardYear = 365

    AnnualInvestmentAllowancePeriods().filter(_.startDateFallsWithin(apStartDate, apEndDate)) match {
      case aiaPeriodsStartingInAP if aiaPeriodsStartingInAP.nonEmpty => standardYear
      case _ => {
        val numDaysInAP = Days.daysBetween(apStartDate, apEndDate).getDays + 1
        numDaysInAP max standardYear
      }
    }
  }

}

case class AnnualInvestmentAllowancePeriod(start: LocalDate, end: LocalDate, maximumAllowed: Int) {

  def encompasses(date: LocalDate): Boolean = {
    !date.isBefore(start) && !date.isAfter(end)
  }

  def fallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = {
    startDate.isBefore(start) && endDate.isAfter(end)
  }
  
  def startDateFallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = {
    (start.isAfter(startDate) || start.equals(startDate)) && (start.isBefore(endDate) || start.equals(endDate))
  }

  def intersectingDays(periodStart: LocalDate, periodEnd: LocalDate): Int = {
    (encompasses(periodStart), encompasses(periodEnd), fallsWithin(periodStart, periodEnd)) match {
      case (true, true, false) => Days.daysBetween(periodStart, periodEnd).getDays + 1
      case (true, false, false) => Days.daysBetween(periodStart, end).getDays + 1
      case (false, true, false) => Days.daysBetween(start, periodEnd).getDays + 1
      case (false, false, true) => Days.daysBetween(start, end).getDays + 1
      case _ => 0
    }
  }
}

object AnnualInvestmentAllowancePeriods {

  def apply() = Set(
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2016-01-01"), end = LocalDate.parse("2016-12-31"), maximumAllowed = 200000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2014-04-01"), end = LocalDate.parse("2015-12-31"), maximumAllowed = 500000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2013-01-01"), end = LocalDate.parse("2014-03-31"), maximumAllowed = 250000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2012-04-01"), end = LocalDate.parse("2012-12-31"), maximumAllowed = 25000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2010-04-01"), end = LocalDate.parse("2012-03-31"), maximumAllowed = 100000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.parse("2008-04-01"), end = LocalDate.parse("2010-03-31"), maximumAllowed = 50000)
  )
}