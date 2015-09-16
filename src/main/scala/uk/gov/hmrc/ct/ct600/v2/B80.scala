package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v2.A6
import A6._

case class B80(value: Option[Boolean]) extends CtBoxIdentifier(name = "B80 - Completed box A11 in the Supplementary Pages CT600A") with CtOptionalBoolean

object B80 extends Calculated[B80, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B80 = {
    calculateB80(fieldValueRetriever.retrieveA11())
  }
}
