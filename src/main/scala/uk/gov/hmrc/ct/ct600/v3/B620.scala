/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B620(value: Option[Int]) extends CtBoxIdentifier("Franked investment") with CtOptionalInteger with Input with ValidatableBox[CT600BoxRetriever] {

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    val bfq1 = boxRetriever.bFQ1()

    failIf (bfq1.value.getOrElse(false) && !hasValue) {
      Set(CtValidation(Some("B620"), "error.B620.required"))
    }
  }
}
