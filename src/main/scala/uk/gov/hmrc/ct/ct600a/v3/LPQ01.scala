/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ01(value: Boolean) extends CtBoxIdentifier(name = "Declare loans to participators") with CtBoolean

object LPQ01 extends Calculated[LPQ01, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(boxRetriever: CT600ABoxRetriever): LPQ01 = {
    calculateLPQ01(
      boxRetriever.lpq04(),
      boxRetriever.lpq10(),
      boxRetriever.a5(),
      boxRetriever.lpq03()
    )
  }

}
