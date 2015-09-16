package uk.gov.hmrc.ct.ct600.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP295, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations.Ct600AnnualConstants._


object CorporationTaxHelper extends CtTypeConverters with NumberRounding {

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): Int = {
    validateAccountingPeriod(params.accountingPeriod)

    val fy1: Int = fallsInFinancialYear(params.accountingPeriod.cp1.value)
    val fy1Result = calculateApportionedProfitsChargeableForYear(fy1, params, constantsForTaxYear(TaxYear(fy1)))

    roundedToIntHalfDown(fy1Result)
  }

  def calculateApportionedProfitsChargeableFy2(params: CorporationTaxCalculatorParameters): Int = {
    validateAccountingPeriod(params.accountingPeriod)

    if (accountingPeriodSpansTwoFinancialYears(params.accountingPeriod)) {
      val fy2 = fallsInFinancialYear(params.accountingPeriod.cp2.value)
      val fy2Result = calculateApportionedProfitsChargeableForYear(fy2, params, constantsForTaxYear(TaxYear(fy2)))
      roundedToIntHalfUp(fy2Result)

    } else {
      0
    }
  }

  private def calculateApportionedProfitsChargeableForYear(year: Int, params: CorporationTaxCalculatorParameters, constants: CtConstants): BigDecimal = {
    val profitsChargeable = BigDecimal(params.profitsChargeableToCT.value)
    val apDaysInFy = accountingPeriodDaysInFinancialYear(year, params.accountingPeriod)

    val apFyRatio = apDaysInFy / daysInAccountingPeriod(params.accountingPeriod)

    val proRataProfitsChargeable = profitsChargeable * apFyRatio

    proRataProfitsChargeable
  }
}

case class CorporationTaxCalculatorParameters(profitsChargeableToCT: CP295,
                                              accountingPeriod: HmrcAccountingPeriod)
