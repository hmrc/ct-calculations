/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtOptionalInteger

object AC26 extends Calculated[AC26, Frs102AccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC26 = boxRetriever match {
      case br: FullAccountsBoxRetriever => calculateAC26(br.ac16(), br.ac18(), br.ac20(), br.ac22())
      case br: AbridgedAccountsBoxRetriever => calculateAC26(br.ac16(), br.ac18(), br.ac20())
    }
}
