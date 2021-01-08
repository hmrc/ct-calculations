/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E185(value: Option[Int]) extends CtBoxIdentifier("Held at the end of the period (use accounts figures): Value of any non-qualifying investments and loans") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    if(boxRetriever.e180().value.contains(CharityLoansAndInvestments.SomeLoansAndInvestments)) {
      validateAsMandatory(this) ++ validatePositiveInteger(this)
    } else {
      Set()
    }
  }

}
