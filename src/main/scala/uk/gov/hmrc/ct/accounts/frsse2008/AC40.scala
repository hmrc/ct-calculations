/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC40(value: Option[Int]) extends CtBoxIdentifier(name = "Current Net balance transferred to reserves") with CtOptionalInteger

object AC40 extends Calculated[AC40, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC40 = {
    calculateCurrentNetBalance(boxRetriever.ac36(), boxRetriever.ac38())
  }
}
