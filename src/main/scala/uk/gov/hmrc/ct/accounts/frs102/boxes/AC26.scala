/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtOptionalInteger

object AC26 extends Calculated[AC26, Frs102AccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC26 = {
    import boxRetriever._

//    val isOPWEnabled: Boolean = cato24.value.isDefined

//    val calculatedAC26 =
//      if(isOPWEnabled)
//      calculateAC26OPW(ac16(), ac18(), ac20(), ac401())
//      else
//      calculateAC26(ac16(), ac18, ac20)
//

    boxRetriever match {
      case br: FullAccountsBoxRetriever => calculateAC26(br.ac16(), br.ac18(), br.ac20(), br.ac22())
      case _: AbridgedAccountsBoxRetriever => calculateAC26(ac16(), ac18(), ac20())
    //  calculatedAC26
    }
    }
}
