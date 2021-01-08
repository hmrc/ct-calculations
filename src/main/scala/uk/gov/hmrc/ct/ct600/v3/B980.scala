/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600DeclarationBoxRetriever

case class B980(value: Option[LocalDate]) extends CtBoxIdentifier("Declaration date")
            with CtOptionalDate with Input with ValidatableBox[CT600DeclarationBoxRetriever] {

  override def validate(boxRetriever: CT600DeclarationBoxRetriever): Set[CtValidation] = validateDateAsMandatory("B980", this)
}
