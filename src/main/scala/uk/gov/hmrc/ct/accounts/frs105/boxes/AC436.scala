/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC436(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss") with CtOptionalInteger

object AC436 extends Calculated[AC436, Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC436 = {
    import boxRetriever._
    calculateAC436(ac13, ac406, ac411, ac416, ac421, ac426, ac35)
  }
}
