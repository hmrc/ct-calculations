/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC27(value: Option[Int]) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtOptionalInteger

object AC27 extends Calculated[AC27, Frs102AccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC27 = {
    boxRetriever match {
      case br: FullAccountsBoxRetriever => calculateAC27(br.ac17(), br.ac19(), br.ac21(), ac23 = br.ac23())
      case br: AbridgedAccountsBoxRetriever => calculateAC27(br.ac17(), br.ac19(), br.ac21(), br.ac25())
    }
  }
}
