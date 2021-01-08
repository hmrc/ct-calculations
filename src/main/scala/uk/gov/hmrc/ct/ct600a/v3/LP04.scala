/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LP04(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of other loans.") with CtOptionalInteger with Input with ValidatableBox[CT600ABoxRetriever] {

  override def validate(boxRetriever: CT600ABoxRetriever) = if (boxRetriever.lpq10().value.getOrElse(false)) validateAsMandatory(this) ++ validatePositiveInteger(this) else Set()

}
