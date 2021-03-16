/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP121(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of Eat Out to Help Out support claimed") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(this),
      validateMoney(this.value, 0, boxRetriever.cp7.value.getOrElse(0))
    )
  }
}
