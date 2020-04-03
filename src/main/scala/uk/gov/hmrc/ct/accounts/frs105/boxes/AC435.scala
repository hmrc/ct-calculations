/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC435(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss") with CtOptionalInteger

object AC435 extends Calculated[AC435, Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC435 = {
    import boxRetriever._
    calculateAC435(ac12, ac405, ac410, ac415, ac420, ac425, ac34, ac401, ac403)
  }
}
