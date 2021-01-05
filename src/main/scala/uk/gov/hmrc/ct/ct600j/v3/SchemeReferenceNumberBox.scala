/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

abstract class SchemeReferenceNumberBox extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600JBoxRetriever] {

  def validateSchemeReferenceNumber(previousSchemeNumberBox: CtOptionalString, previousSchemeDateBox: CtOptionalDate, schemeDateBox: CtOptionalDate) = (previousSchemeNumberBox.value, previousSchemeDateBox.value, schemeDateBox.value) match {
    case (None, None, _) => validateStringAsBlank(id, this)
    case (_, _, Some(_)) => validateAsMandatory(this) ++ validateOptionalStringByRegex(id, this, taxAvoidanceSchemeNumberRegex)
    case _ => validateOptionalStringByRegex(id, this, taxAvoidanceSchemeNumberRegex)
  }
}
