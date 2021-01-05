/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, EndDate}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, EndDate}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

package object offPayRollWorking {
  val opwApplies2020 = new LocalDate("2017-04-05")

  def isOPWEnabled(apEndDate: LocalDate) = apEndDate.isAfter(opwApplies2020)

  def DeductionCannotBeGreaterThanProfit(profit: CtOptionalInteger, loss: CtOptionalInteger): Set[CtValidation] ={

    (loss.value, profit.value) match {
      case (Some(lossValue),Some(profitValue)) if lossValue > profitValue => Set(CtValidation(Some(loss.getClass.getSimpleName), s"error.${loss.getClass.getSimpleName}.exceeds.${profit.getClass.getSimpleName}"))
      case (_ , _) => Set.empty
    }
  }
}
