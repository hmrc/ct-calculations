package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod


trait HmrcAccountingPeriodCalculator {

  def accountingPeriod(params: HmrcAccountingPeriodParameters) : HmrcAccountingPeriodResult = {
    HmrcAccountingPeriodResult(effectiveDate(params.accountingPeriod.cp1.value, params.userStartDate),
                               effectiveDate(params.accountingPeriod.cp2.value, params.userEndDate))
  }

  private def effectiveDate(prepopDate: LocalDate, userDate: Option[LocalDate]) = userDate.getOrElse(prepopDate)
}

case class HmrcAccountingPeriodParameters(accountingPeriod: HmrcAccountingPeriod, userStartDate: Option[LocalDate], userEndDate: Option[LocalDate])

case class HmrcAccountingPeriodResult(startDate: LocalDate, endDate: LocalDate)