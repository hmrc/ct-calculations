

package uk.gov.hmrc.ct.computations.nir

import uk.gov.hmrc.ct.box.{EndDate, StartDate}
import uk.gov.hmrc.ct.computations.losses.northernIrelandJourneyActive
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.{CP997c, CP997e, CPQ19, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.calculations._
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

import scala.math.BigDecimal.RoundingMode

trait NorthernIrelandCalculations extends HmrcValueApportioning {
  import Ct600AnnualConstants._

  def revaluedNirLossesAgainstNonTradingProfit(ct600AnnualConstants: Ct600AnnualConstants)
                                              (cp997c: CP997c,
                                               hmrcAccountingPeriod: HmrcAccountingPeriod,
                                               cpq19: CPQ19): CP997e = {

    CP997e(cp997c.value.map { nirLosses =>

      val apportionedValues: Map[TaxYear, Int] =
        calculateApportionedValuesForAccountingPeriod(nirLosses, hmrcAccountingPeriod)

      apportionedValues.map {
        case (taxYear, apportionedLoss) =>
          val ctRates = ct600AnnualConstants.constantsForTaxYear(taxYear)
          ctRates match {
            case nir: NorthernIrelandRate =>
              (BigDecimal(apportionedLoss) * nir.revaluationRatio)
                .setScale(0, RoundingMode.DOWN).toInt
            case _ => apportionedLoss
          }
      }.sum
    })
  }

  def nIRrateOfTaxFy1(start: StartDate): Option[BigDecimal] = {
    val ct600Annuals = getConstantsFromYear(
      startingFinancialYear(start)
    )

    getNirRate(ct600Annuals)
  }

  def nIRrateOfTaxFy2(end: EndDate): Option[BigDecimal] = {
    val ct600Annuals = getConstantsFromYear(
      endingFinancialYear(end)
    )

    getNirRate(ct600Annuals)
  }

  private def getNirRate(ct600Annuals: CtConstants) = {
    ct600Annuals match {
      case nir: NorthernIrelandRate => Some(nir.northernIrelandRate)
      case _ => None
    }
  }

  def nirOpt[A](computationsBoxRetriever: ComputationsBoxRetriever)(r: => A): Option[A] = computationsBoxRetriever match {
    case retriever: ComputationsBoxRetriever with AboutThisReturnBoxRetriever =>
      if (northernIrelandJourneyActive(retriever))
        Some(r)
      else None
    case _ => None
  }

  def calculateTaxForTradingProfitForFirstFinancialYear(b350: B350, b355: B355): B360 = {
    B360(
      for {
        profit <- b350.value
        rate <- b355.value
      } yield profit * rate
    )
  }

  def calculateTaxForTradingProfitForSecondFinancialYear(b400: B400, b405: B405): B410 = {
    B410(
      for {
        profit <- b405.value
        rate <- b400.value
      } yield profit * rate
    )
  }
}
