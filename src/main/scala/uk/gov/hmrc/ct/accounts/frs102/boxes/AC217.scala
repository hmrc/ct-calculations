/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC217(value: Option[Int]) extends CtBoxIdentifier(name = "The total cost or valuation of all tangible assets at the end of the period")
  with CtOptionalInteger {
}

object AC217 extends Calculated[AC217, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC217 = {
    calculateTangibleAssetsAtTheEndOFThePeriod(
      boxRetriever.ac124(),
      boxRetriever.ac125(),
      boxRetriever.ac126(),
      boxRetriever.ac212(),
      boxRetriever.ac213()
    )
  }
}
