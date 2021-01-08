/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, _}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E26(value: Option[Int]) extends CtBoxIdentifier("Qualifying investments and loans") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}

object CharityLoansAndInvestments {
  val AllLoansAndInvestments = 1
  val SomeLoansAndInvestments = 2
  val NoLoansNorInvestments = 3
}
