/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Current Gross profit or loss") with CtOptionalInteger

object AC16 extends Calculated[AC16, Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC16 = {
    calculateCurrentGrossProfitOrLoss(ac12 = boxRetriever.ac12(),
                                      ac14 = boxRetriever.ac14(),
                                      statutoryAccountsFiling = boxRetriever.statutoryAccountsFiling(),
                                      ac401 = boxRetriever.ac401(),
                                      ac403 = boxRetriever.ac403())
  }
}
