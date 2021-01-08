/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP6(value: Int) extends CtBoxIdentifier(name = "Apportioned Profit or Loss") with CtInteger

object CP6 extends Calculated[CP6, ComputationsBoxRetriever] {
  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP6 = {
    ???
  }
}
