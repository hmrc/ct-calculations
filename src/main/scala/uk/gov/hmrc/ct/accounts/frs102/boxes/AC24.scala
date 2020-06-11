/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

  import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
  import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
  import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, Input}

  case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (current PoA)")
    with CtOptionalInteger
    with Input


  object AC24 extends Calculated[AC24, Frs102AccountsBoxRetriever] with GrossProfitAndLossCalculator {
    override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC24 = {
      import boxRetriever._

      boxRetriever match {
        case br: FullAccountsBoxRetriever => calculateAC24Full(br.ac12(), br.ac401(), br.ac403(), br.ac14())
        case _: AbridgedAccountsBoxRetriever => calculateAC24Abridged(ac16(), ac401(), ac403())
      }
    }
  }