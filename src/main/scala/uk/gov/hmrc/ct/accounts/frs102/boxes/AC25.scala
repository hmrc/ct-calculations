/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, Input}

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Gross surplus or (deficit) (previous PoA)")
  with CtOptionalInteger

object AC25 extends Calculated[AC25, Frs102AccountsBoxRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC25 = {
    import boxRetriever._

    boxRetriever match {
      case br: FullAccountsBoxRetriever => calculateAC25Full(br.ac13(), br.ac402(), br.ac404(), br.ac15())
      case _: AbridgedAccountsBoxRetriever => calculateAC25Abridged(ac17(), ac402(), ac404())
    }
  }
}

