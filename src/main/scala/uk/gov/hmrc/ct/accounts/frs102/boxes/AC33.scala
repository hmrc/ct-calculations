/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC33(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss before tax (previous PoA)") with CtOptionalInteger

object AC33 extends Calculated[AC33, Frs102AccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC33 = {
    import boxRetriever._
    calculateAC33(ac27(), ac29(), ac31())
  }
}
