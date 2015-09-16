package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B620(value: Option[Int]) extends CtBoxIdentifier("Franked investment") with CtOptionalInteger with Input with ValidatableBox[CT600BoxRetriever] {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    val bfq1 = boxRetriever.retrieveBFQ1()

    if(bfq1.value.getOrElse(false)) {
      if (!this.value.isDefined) Set(CtValidation(Some("B620"), "error.B620.required")) else Set()
    } else Set()
  }
}
