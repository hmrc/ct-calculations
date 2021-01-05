/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

/*
 * Available values are:
 * AllLoansAndInvestments = 1
 * SomeLoansAndInvestments = 2
 * NoLoansNorInvestments = 3
 */

case class E180(value: Option[Int]) extends CtBoxIdentifier("Qualifying investments and loans") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}

object CharityLoansAndInvestments {
  val AllLoansAndInvestments = 1
  val SomeLoansAndInvestments = 2
  val NoLoansNorInvestments = 3
}
