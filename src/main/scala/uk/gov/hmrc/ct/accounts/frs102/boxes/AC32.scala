/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC32(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss before tax (current PoA)") with CtOptionalInteger

object AC32 extends Calculated[AC32, Frs102AccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC32 = {
    import boxRetriever._
    calculateAC32(ac26(), ac28(), ac30())
  }
}
