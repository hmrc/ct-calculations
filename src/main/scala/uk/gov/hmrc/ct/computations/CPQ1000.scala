/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ1000(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you buy any company cars in this period?") with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.cpQ7(), value) match {
      case (CPQ7(Some(true)), None) => validateBooleanAsMandatory("CPQ1000", this)
      case (CPQ7(Some(false)) | CPQ7(None), Some(_)) => Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      case _ => Set.empty
    }
  }
}
