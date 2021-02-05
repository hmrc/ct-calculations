/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC415
import uk.gov.hmrc.ct.accounts.frs10x.AC13
import uk.gov.hmrc.ct.accounts.frsAny.boxes.AC14
import uk.gov.hmrc.ct.accounts.frsse2008._
import uk.gov.hmrc.ct.accounts.frsse2008.micro._
import uk.gov.hmrc.ct.box.CtOptionalInteger
import uk.gov.hmrc.ct.{MicroEntityFiling, StatutoryAccountsFiling}

trait ProfitOrLossCalculator {

  def calculateCurrentProfitOrLoss(ac12: AC12, ac405: AC405, ac410: AC410, ac415: AC415, ac420: AC420, ac425: AC425, ac34: AC34, ac401: AC401, ac403: AC403, microEntityFiling: MicroEntityFiling): AC435 = {
    AC435 (
      if (microEntityFiling.value) {
        calculateMicroProfitAndLoss(turnover = ac12, otherIncome = ac405, rawMaterials = ac410,
                                    staffCosts = ac415, depreciation = ac420,
                                    otherCharges = ac425, tax = ac34, opwTurnover = ac401, opwDeduction = ac403)
      }
      else None
    )
  }

  def calculatePreviousProfitOrLoss(ac13: AC13, ac406: AC406, ac411: AC411, ac416: AC416, ac421: AC421, ac426: AC426, ac35: AC35,ac402: AC402, ac404: AC404, microEntityFiling: MicroEntityFiling): AC436 = {
    AC436 (
      if (microEntityFiling.value) {
        calculateMicroProfitAndLoss(turnover = ac13, otherIncome = ac406, rawMaterials = ac411,
                                    staffCosts = ac416, depreciation = ac421,
                                    otherCharges = ac426, tax = ac35, opwTurnover = ac402, opwDeduction = ac404)
      }
      else None
    )
  }

  private def calculateMicroProfitAndLoss(turnover: CtOptionalInteger, otherIncome: CtOptionalInteger,
                                          rawMaterials: CtOptionalInteger, staffCosts: CtOptionalInteger,
                                          depreciation: CtOptionalInteger, otherCharges: CtOptionalInteger, tax: CtOptionalInteger, opwTurnover: CtOptionalInteger, opwDeduction: CtOptionalInteger): Option[Int] = {

    def enteredValuesOnly(seq: Seq[CtOptionalInteger]): Seq[Int] = {
      seq.filterNot(_.value.isEmpty).map(_.orZero)
    }

    val additions = enteredValuesOnly(Seq(turnover, otherIncome, opwTurnover))
    val deductions = enteredValuesOnly(Seq(rawMaterials, staffCosts, depreciation, otherCharges, tax, opwDeduction))

    if (additions.nonEmpty || deductions.nonEmpty) {
      Some(additions.sum - deductions.sum)
    } else None
  }


  def calculateCurrentGrossProfitOrLoss(ac12: AC12, ac14: AC14, ac401: AC401, ac403: AC403, statutoryAccountsFiling: StatutoryAccountsFiling): AC16 = {
    AC16(
      if (statutoryAccountsFiling.value) {
        calculateProfitOrLoss(ac12.value, ac14.value, ac401.value, ac403.value)
      }
      else None
    )
  }

  def calculatePreviousGrossProfitOrLoss(ac13: AC13, ac15: AC15, ac402: AC402, ac404: AC404, statutoryAccountsFiling: StatutoryAccountsFiling): AC17 = {
    AC17(
      if (statutoryAccountsFiling.value) {
        calculateProfitOrLoss(ac13.value, ac15.value, ac402.value, ac404.value)
      }
      else None
    )
  }

  def calculateCurrentOperatingProfitOrLoss(ac16: AC16, ac18: AC18,
                                            ac20: AC20, ac22: AC22): AC26 = {
    AC26(calculateOperatingProfitOrLoss(ac16.value, ac18.value, ac20.value, ac22.value))
  }

  def calculatePreviousOperatingProfitOrLoss(ac17: AC17, ac19: AC19,
                                             ac21: AC21, ac23: AC23): AC27 = {
    AC27(calculateOperatingProfitOrLoss(ac17.value, ac19.value, ac21.value, ac23.value))
  }

  def calculateCurrentProfitOrLossBeforeTax(ac26: AC26, ac28: AC28, ac30: AC30): AC32 = {
    AC32(calculateProfitOrLossBeforeTax(operatingProfit = ac26.value, interestRecieved = ac28.value, interestedPayable = ac30.value))
  }

  def calculatePreviousProfitOrLossBeforeTax(ac27: AC27, ac29: AC29, ac31: AC31): AC33 = {
    AC33(calculateProfitOrLossBeforeTax(operatingProfit = ac27.value, interestRecieved = ac29.value, interestedPayable = ac31.value))
  }

  def calculateCurrentProfitOtLossAfterTax(ac32: AC32, ac34: AC34): AC36 = {
    AC36(calculateProfitOrLossAfterTax(ac32.value, ac34.value))
  }

  def calculatePreviousProfitOtLossAfterTax(ac33: AC33, ac35: AC35): AC37 = {
    AC37(calculateProfitOrLossAfterTax(ac33.value, ac35.value))
  }

  def calculateCurrentNetBalance(ac36: AC36, ac38: AC38): AC40 = {
    AC40(calculateNetBalance(ac36.value, ac38.value))
  }

  def calculatePreviousNetBalance(ac37: AC37, ac39: AC39): AC41 = {
    AC41(calculateNetBalance(ac37.value, ac39.value))
  }

  private def calculateProfitOrLossAfterTax(profitBeforeTax: Option[Int], tax: Option[Int]): Option[Int] = {
    profitBeforeTax.map { p =>
      p - tax.getOrElse(0)
    }
  }

  private def calculateNetBalance(profitafterTax: Option[Int], dividends: Option[Int]): Option[Int] = {
    profitafterTax.map { p =>
      p - dividends.getOrElse(0)
    }
  }

  private def calculateProfitOrLossBeforeTax(operatingProfit: Option[Int], interestRecieved: Option[Int], interestedPayable: Option[Int]) : Option[Int] = {
    operatingProfit.map { op =>
      op + interestRecieved.getOrElse(0) - interestedPayable.getOrElse(0)
    }
  }

  private def calculateProfitOrLoss(turnover: Option[Int], costs: Option[Int], opwTurnover: Option[Int], opwCost: Option[Int]): Option[Int] = {
    (turnover, costs, opwTurnover, opwCost) match {
      case (None, None, None, None) => None
      case (t, c, ot, oc) => Some((t.getOrElse(0)+ ot.getOrElse(0)) - (c.getOrElse(0) + oc.getOrElse(0)))
    }
  }

  private def calculateOperatingProfitOrLoss(grossProfit: Option[Int],
                                             distributionCosts: Option[Int],
                                             administrativeExpenses: Option[Int],
                                             otherOperatingIncome: Option[Int]): Option[Int] = {
    grossProfit.map { gp =>
      gp - distributionCosts.getOrElse(0) - administrativeExpenses.getOrElse(0) + otherOperatingIncome.getOrElse(0)
    }
  }
}
