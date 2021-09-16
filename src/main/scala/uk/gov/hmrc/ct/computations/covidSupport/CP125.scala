/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox, Validators}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP125(value: Option[Int])extends CtBoxIdentifier(name = "Enter the amount of Eat Out to Help Out overpayments not repaid") with
  CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] with Validators {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val amountReceivedFromEotho = boxRetriever.cp121().orZero

    collectErrors(
      validateZeroOrPositiveInteger(this),
      exceedsMax(value, amountReceivedFromEotho)
    )
  }
}
