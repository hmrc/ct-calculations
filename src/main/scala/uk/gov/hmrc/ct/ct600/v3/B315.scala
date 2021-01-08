/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.computations.losses._

// was B37
case class B315(value: Int) extends CtBoxIdentifier(name = "Profits chargeable to corporation tax") with CtInteger

object B315 extends Calculated[B315,CT600BoxRetriever] {

  override def calculate(boxRetriever: CT600BoxRetriever): B315 = {

    B315(boxRetriever.cp295().value)

  }

}