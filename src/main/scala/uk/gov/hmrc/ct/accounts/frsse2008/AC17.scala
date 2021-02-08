/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Gross profit or loss") with CtOptionalInteger

object AC17 extends Calculated[AC17, Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC17 = {
    calculatePreviousGrossProfitOrLoss(ac13 = boxRetriever.ac13(),
                                       ac15 = boxRetriever.ac15(),
                                       statutoryAccountsFiling = boxRetriever.statutoryAccountsFiling(),
                                       ac402 = boxRetriever.ac402(),
                                       ac404 = boxRetriever.ac404())
  }
}
