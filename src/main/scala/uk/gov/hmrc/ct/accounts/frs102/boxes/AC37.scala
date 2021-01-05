/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC37(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)") with CtOptionalInteger

object AC37 extends Calculated[AC37, Frs102AccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC37 = {
    import boxRetriever._
    calculateAC37(ac33(), ac35())
  }
}
