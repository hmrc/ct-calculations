/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.micro

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC436(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss") with CtOptionalInteger

object AC436 extends Calculated[AC436, Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC436 = {
    calculatePreviousProfitOrLoss(ac13 = boxRetriever.ac13(), ac406 = boxRetriever.ac406(),
                                  ac411 = boxRetriever.ac411(), ac416 = boxRetriever.ac416(),
                                  ac421 = boxRetriever.ac421(), ac426 = boxRetriever.ac426(),
                                  ac35 = boxRetriever.ac35(), ac402 = boxRetriever.ac402() ,
                                  ac404 = boxRetriever.ac404(), boxRetriever.microEntityFiling())
  }
}
